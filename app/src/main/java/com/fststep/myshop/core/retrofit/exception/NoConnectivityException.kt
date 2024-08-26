package com.fststep.myshop.core.retrofit.exception

import com.fststep.myshop.core.retrofit.exception.ErrorBody

/**
 * Created by Shubham Singh on 26/06/21.
 */

class NoConnectivityException : RuntimeException() {
    companion object {

        val localNetworkErrorBody: ErrorBody
            get() {
                val errorBody = ErrorBody()
                errorBody.code = "500"
                errorBody.message = "No internet connectivity error"
                return errorBody
            }
    }
}