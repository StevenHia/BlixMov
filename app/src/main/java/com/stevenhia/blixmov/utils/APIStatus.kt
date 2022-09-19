package com.stevenhia.blixmov.utils

class Status<out T>(val status: APIStatus?, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Status<T> {
            return Status(APIStatus.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T?): Status<T> {
            return Status(APIStatus.ERROR, data, message)
        }

        fun <T> error404(message: String, data: T?): Status<T> {
            return Status(APIStatus.ERROR404, data, message)
        }
    }
}

enum class APIStatus {
    SUCCESS,
    ERROR,
    ERROR404
}