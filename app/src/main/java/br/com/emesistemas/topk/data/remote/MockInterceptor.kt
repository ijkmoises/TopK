package br.com.emesistemas.topk.data.remote

import br.com.emesistemas.topk.BuildConfig
import br.com.emesistemas.topk.app.App
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {

    @Suppress("ConstantConditionIf")
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.BUILD_TYPE == "fortest") {

            val uri = chain.request().url.toUri().toString()

            val responseString = when {
                uri.contains("mock") -> App.getMockRepos()
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            return chain.proceed(chain.request())
        }
    }
}