package br.com.emesistemas.topk.data.remote.interceptor

import br.com.emesistemas.topk.BuildConfig
import br.com.emesistemas.topk.app.App
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        println("moises ramos intercept "+BuildConfig.IS_UI_TESTING.get())

        if (BuildConfig.IS_UI_TESTING.get()) {
            val responseString = App.getMockRepos()
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