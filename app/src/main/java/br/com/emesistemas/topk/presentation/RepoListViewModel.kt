package br.com.emesistemas.topk.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.emesistemas.topk.data.local.RepoRepository
import br.com.emesistemas.topk.data.remote.Resource
import br.com.emesistemas.topk.model.Repo
import kotlinx.coroutines.Dispatchers

class RepoListViewModel(private val repository: RepoRepository) : ViewModel() {

    private var totalPage = 1000
    private var currentPage = 0
    private var isLastPage = false
    private var isLoading = false

    fun isLastPage(): Boolean {
        return isLastPage
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    fun fetchRepoKotlinList(): LiveData<Resource<Repo>> = liveData(Dispatchers.IO) {
        if (hasNext()) {
            currentPage++
            isLoading = true

            emit(Resource.loading())
            emit(repository.fetchCached(currentPage))

            emit(Resource.loading())
            emit(repository.fetchRemote(currentPage))

            isLoading = false
        } else {
            isLastPage = true
        }
    }

    private fun hasNext() = currentPage < totalPage
}