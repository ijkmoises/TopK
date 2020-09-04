package br.com.emesistemas.topk.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class SearchRepoParamsInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val originalHttpUrl = original.url
        val url = originalHttpUrl
            .newBuilder()
            .addQueryParameter("per_page", "20")
            .addQueryParameter("order", "desc")
            .addQueryParameter("sort", "stars")
            .addQueryParameter("q", "language:kotlin")
            .build()

        val requestBuilder = original.newBuilder().url(url).also {
            it.header("accept", "application/vnd.github.v3+json").build()
        }

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}