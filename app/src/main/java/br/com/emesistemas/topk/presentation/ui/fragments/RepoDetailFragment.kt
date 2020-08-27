package br.com.emesistemas.topk.presentation.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.emesistemas.topk.databinding.FragmentRepoDetailBinding
import br.com.emesistemas.topk.presentation.viewmodel.UiComponent
import br.com.emesistemas.topk.presentation.viewmodel.UiStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepoDetailFragment : Fragment(), View.OnClickListener {

    private val uiStateViewModel: UiStateViewModel by sharedViewModel()
    private val args by navArgs<RepoDetailFragmentArgs>()
    private lateinit var viewDataBinding: FragmentRepoDetailBinding
    private val repoItem by lazy {
        args.repoItem
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentRepoDetailBinding
            .inflate(inflater, container, false)
        viewDataBinding.openInWeb = this
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiStateViewModel.hasComponent =
            UiComponent(
                homeAsUpButton = true,
                titleToolbar = false
            )
        viewDataBinding.item = repoItem
    }

    private fun openRepoInBrowser(url: String?) {
        url?.let {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(it)
            )
            startActivity(browserIntent)
        }
    }

    override fun onClick(v: View?) {
        openRepoInBrowser(repoItem.owner.html_url)
    }
}