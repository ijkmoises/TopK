package br.com.emesistemas.topk.data.remote

import br.com.emesistemas.topk.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
            .addInterceptor(object : Interceptor {
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
            }).build()
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