package br.com.emesistemas.topk.data.remote

import br.com.emesistemas.topk.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

const val INTERNET_OFF = -1
const val TIMEOUT = 0
const val UNAUTHORIZED = 401
const val FORBIDDEN = 403
const val NOT_FOUND = 404
const val INTERNAL_SERVER_ERROR = 500
const val SERVICE_UNAVAILABLE = 503

open class ResponseHandler {

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is UnknownHostException -> Resource.error(getErrorMessage(INTERNET_OFF), null)
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(TIMEOUT), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): Int {
        return when (code) {
            INTERNET_OFF -> R.string.internet_offline
            TIMEOUT -> R.string.timeout
            UNAUTHORIZED -> R.string.unauthorized
            NOT_FOUND -> R.string.not_found
            INTERNAL_SERVER_ERROR -> R.string.internal_server_error
            SERVICE_UNAVAILABLE -> R.string.service_unavailable
            FORBIDDEN -> R.string.rate_limit_exceeded
            else -> R.string.unexpected_error
        }
    }
}