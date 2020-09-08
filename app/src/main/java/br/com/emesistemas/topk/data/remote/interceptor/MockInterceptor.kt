package br.com.emesistemas.topk.data.remote.interceptor

import android.annotation.SuppressLint
import br.com.emesistemas.topk.BuildConfig
import br.com.emesistemas.topk.app.App
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class MockInterceptor : Interceptor {

    @SuppressLint("VisibleForTests")
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
            .newBuilder()
            .protocol(Protocol.HTTP_2)
            .addHeader("content-type", "application/json")

        when {
            BuildConfig.IS_TESTING_MOCK_API_RESPONSE_OK.get() -> {
                val responseString = App.getMockRepos()
                return response
                    .code(200)
                    .message(responseString)
                    .body(
                        responseString.toByteArray()
                            .toResponseBody("application/json".toMediaTypeOrNull())
                    ).build()
            }
            BuildConfig.IS_TESTING_MOCK_API_RESPONSE_ERROR.get() -> {
                return response.code(500)
                    .build()
            }
            else -> {
                response.build().close()
                return chain.proceed(chain.request())
            }
        }
    }
}