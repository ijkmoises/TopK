package br.com.emesistemas.topk.data.remote

import br.com.emesistemas.topk.BuildConfig
import br.com.emesistemas.topk.data.remote.interceptor.MockInterceptor
import br.com.emesistemas.topk.data.remote.interceptor.SearchRepoParamsInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AppHttp {

    private val httpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .addInterceptor(MockInterceptor())
            .addInterceptor(SearchRepoParamsInterceptor())
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.URLBASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    val githubApi: GithubApi by lazy {
        retrofit.create(GithubApi::class.java)
    }
}