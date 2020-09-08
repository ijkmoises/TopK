package br.com.emesistemas.topk.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.emesistemas.topk.data.local.RepoRepository
import br.com.emesistemas.topk.data.remote.Resource
import br.com.emesistemas.topk.model.Repo
import kotlinx.coroutines.Dispatchers

class RepoListViewModel(private val repository: RepoRepository) : ViewModel() {

    private var totalPage = 1000
    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingPage = false

    fun isLastPage(): Boolean {
        return isLastPage
    }

    fun isLoadingPage(): Boolean {
        return isLoadingPage
    }

    fun fetchListResult() = liveData(Dispatchers.IO) {
        if (hasNext()) {
            isLoadingPage = true

            val fromCacheResource = repository.fetchCached(page = currentPage)
            emit(fromCacheResource)

            emit(Resource.loading())
            val fromApiResource = repository.fetchRemote(page = currentPage)
            emit(fromApiResource)

            incrementPageCount(fromCacheResource, fromApiResource)

            isLoadingPage = false
        } else {
            isLastPage = true
        }
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun incrementPageCount(
        fromCacheResource: Resource<Repo>,
        fromApiResource: Resource<Repo>
    ) {
        //Avoid null pointer exception when test this ViewModel
        if (fromCacheResource == null || fromApiResource == null) {
            return
        }

        if ((fromCacheResource.data != null
                    && fromCacheResource.data.items.isNotEmpty())
            || (fromApiResource.data != null
                    && fromApiResource.data.items.isNotEmpty())
        ) {
            currentPage++
        }
    }

    private fun hasNext() = currentPage < totalPage
}