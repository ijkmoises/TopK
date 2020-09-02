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

    suspend fun fetchCached(page: Int): Resource<Repo> {
        return responseHandler.handleSuccess(Repo(dao.getByPage(page)))
    }

    suspend fun fetchRemote(page: Int): Resource<Repo> {
        return try {
            val fromApi = api.fetchKotlinRepositories(page)
            cache(page, fromApi.items)
            responseHandler.handleSuccess(Repo(dao.getByPage(page)))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    private suspend fun cache(page: Int, repoItems: List<Item>) {
        repoItems.forEach { it.page = page }
        dao.insertAll(repoItems)
    }
}