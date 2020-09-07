package br.com.emesistemas.topk.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.emesistemas.topk.data.local.RepoRepository
import br.com.emesistemas.topk.data.remote.Resource
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

    fun onItemsVisibleInRecyclerView(){
        currentPage++
    }

    fun fetchListResult() = liveData(Dispatchers.IO) {

        if (hasNext()) {

            isLoading = true

            emit(repository.fetchCached(page = currentPage))

            emit(Resource.loading())

            emit(repository.fetchRemote(page = currentPage))

            isLoading = false
        } else {
            isLastPage = true
        }
    }

    private fun hasNext() = currentPage < totalPage
}