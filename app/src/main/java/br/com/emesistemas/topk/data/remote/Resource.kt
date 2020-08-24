package br.com.emesistemas.topk.data.remote

import br.com.emesistemas.topk.data.remote.Status.*

data class Resource<out T>(val status: Status, val data: T?, val message: Int?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: Int, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}