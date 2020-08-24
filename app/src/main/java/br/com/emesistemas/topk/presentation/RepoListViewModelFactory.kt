package br.com.emesistemas.topk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.emesistemas.topk.data.local.RepoRepository

class RepoListViewModelFactory(
    private val repository: RepoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoListViewModel(repository) as T
    }
}