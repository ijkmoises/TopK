package br.com.emesistemas.topk.data.local

import br.com.emesistemas.topk.data.remote.GithubApi
import br.com.emesistemas.topk.data.remote.Resource
import br.com.emesistemas.topk.data.remote.ResponseHandler
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.model.Repo

class RepoRepository(
    private val dao: ItemDao,
    private val api: GithubApi,
    private val responseHandler: ResponseHandler
) {

    lateinit var resource :Resource<Repo>

//    suspend fun fetch(page: Int): Resource<Repo>{
//        try {
//            resource = fetchCached(page)
//
//            val fromApi = fetchRemote(page)
//
//            cache(page, fromApi.items)
//
//            resource = responseHandler.handleSuccess(fromApi)
//
//        } catch (e: Exception) {
//            resource = responseHandler.handleException(e)
//
//        } finally {
//            return resource
//        }
//    }

//    suspend fun fetchCached(page: Int): Resource<Repo> {
//        return responseHandler.handleSuccess(Repo(dao.getByPage(page)))
//    }
//
//    suspend fun fetchRemote(page: Int): Repo {
//        return api.fetchKotlinRepositories(page)
//    }

    suspend fun fetchCached(page: Int): Resource<Repo> {
        return responseHandler.handleSuccess(Repo(dao.getByPage(page)))
    }

    suspend fun fetchRemote(page: Int): Resource<Repo> {
        return try {
            val fromApi = api.fetchKotlinRepositories(page)
            cache(page, fromApi.items)
            responseHandler.handleSuccess(fromApi)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    private suspend fun cache(page: Int, repoItems: List<Item>) {
        for (item in repoItems) {
            item.page = page
        }
        dao.insertAll(repoItems)
    }
}