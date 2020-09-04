package br.com.emesistemas.topk.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.data.remote.Status
import br.com.emesistemas.topk.databinding.FragmentRepoListBinding
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.presentation.ui.adapters.RepoListAdapter
import br.com.emesistemas.topk.presentation.ui.custom.CustomDividerItemDecoration
import br.com.emesistemas.topk.presentation.ui.custom.PaginationListener
import br.com.emesistemas.topk.presentation.viewmodel.RepoListViewModel
import br.com.emesistemas.topk.presentation.viewmodel.UiComponent
import br.com.emesistemas.topk.presentation.viewmodel.UiStateViewModel
import br.com.emesistemas.topk.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment : Fragment(), View.OnClickListener {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val uiStateViewModel: UiStateViewModel by sharedViewModel()
    private val viewModel by viewModel<RepoListViewModel>()
    val adapter: RepoListAdapter by inject()
    private val navController by lazy {
        findNavController()
    }

    private lateinit var viewBinding: FragmentRepoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.onClick = { item -> openDetail(item) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fetchRepoKotlinList()
        viewBinding = FragmentRepoListBinding.inflate(
            inflater,
            container,
            false
        )
        viewBinding.containerListMessage.fetchRepo = this
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiStateViewModel.hasComponent =
            UiComponent(
                homeAsUpButton = false,
                titleToolbar = true
            )
        configureRecyclerView()
        configureRecyclerViewPagingListener()
    }

    private fun configureRecyclerView() {
        linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvRepoList.layoutManager = linearLayoutManager
        rvRepoList.itemAnimator = DefaultItemAnimator()
        rvRepoList.addItemDecoration(CustomDividerItemDecoration(context, R.drawable.divider))
        rvRepoList.adapter = adapter
    }

    private fun configureRecyclerViewPagingListener() {
        rvRepoList.addOnScrollListener(object : PaginationListener(linearLayoutManager, 20) {
            override fun loadMoreItems() {
                fetchRepoKotlinList()
            }

            override fun isLastPage(): Boolean {
                return viewModel.isLastPage()
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading()
            }
        })
    }

    fun fetchRepoKotlinList() {
        viewModel.fetchListResult().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    resource.data?.let { repo ->
                        adapter.submitList(repo.items)
                    }
                }
                Status.ERROR -> {
                    hideLoading()
                    if (adapter.isEmpty()) {
                        showNoRepoToDisplay()
                    }
                    showError(resource.message)
                }
                Status.LOADING -> {
                    hideNoRepoToDisplay()
                    showLoading()
                }
            }
        })
    }

    private fun showNoRepoToDisplay() {
        rvRepoList.visibility = GONE
        viewBinding.containerListMessage.root.visibility = VISIBLE
    }

    private fun hideNoRepoToDisplay() {
        viewBinding.containerListMessage.root.visibility = GONE
        rvRepoList.visibility = VISIBLE
    }

    private fun showLoading() {
        EspressoIdlingResource.increment()
        progressBar.visibility = VISIBLE
    }

    private fun hideLoading() {
        EspressoIdlingResource.decrement()
        progressBar.visibility = GONE
    }

    private fun showError(message: Int?) {
        message?.let {
            Toast.makeText(
                context, getString(message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openDetail(item: Item) {
        val direction = RepoListFragmentDirections
            .actionRepositoryListToRepositoryDetail(repoItem = item)
        navController.navigate(direction)
    }

    override fun onClick(v: View?) {
        fetchRepoKotlinList()
    }
}