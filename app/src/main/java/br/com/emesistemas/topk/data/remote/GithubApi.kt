package br.com.emesistemas.topk.data.remote

import br.com.emesistemas.topk.model.Repo
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("repositories")
    suspend fun fetchKotlinRepositories(
        @Query("page") page: Int
    ): Repo
}