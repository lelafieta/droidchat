package com.futurist.droidchat.model

import android.R.id.message

sealed class NetworkException(message: String, cause: Throwable?) : Exception(message, cause) {
    class ApiException(val responseMessage: String, val statusCode: Int) :
        NetworkException(responseMessage, null)
    class UnknownException(cause: Throwable? = null) : NetworkException("An unknown error aoccured", cause)
}