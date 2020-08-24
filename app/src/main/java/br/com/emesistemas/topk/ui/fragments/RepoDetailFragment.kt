package br.com.emesistemas.topk.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.extension.getddMMyy
import br.com.emesistemas.topk.presentation.UiComponent
import br.com.emesistemas.topk.presentation.UiStateViewModel
import br.com.emesistemas.topk.ui.custom.ImageLoader
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepoDetailFragment : Fragment() {

    private val uiStateViewModel: UiStateViewModel by sharedViewModel()
    private val args by navArgs<RepoDetailFragmentArgs>()

    private val repoItem by lazy {
        args.repoItem
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_repo_detail,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiStateViewModel.hasComponent = UiComponent(
            homeAsUpButton = true,
            titleToolbar = false
        )

        ImageLoader.download(context,repoItem.owner.avatar_url,ivAvatar)
        tvAutor.text = repoItem.owner.login
        tvRepo.text = repoItem.name
        tvStars.text = repoItem.stargazers_count.toString()
        tvForks.text = repoItem.forks_count.toString()
        tvIssue.text = repoItem.open_issues.toString()
        tvCreated.text = getString(R.string.createdAt,repoItem.created_at?.getddMMyy())
        tvUpdated.text = getString(R.string.updatedAt,repoItem.updated_at?.getddMMyy())

        btOpenWeb.setOnClickListener{
            openRepoInBrowser(repoItem.owner.html_url)
        }
    }

    private fun openRepoInBrowser(url:String?){
        url?.let{
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(it)
            )
            startActivity(browserIntent)
        }
    }
}