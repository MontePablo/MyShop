package com.fststep.myshop.core.model

data class CommonResponse(
    val data: Any?=null,
    val errors: Error?=null,
    val timestamp: String? = null
)

data class Error(
    val message:String?=null,
    val statusCode:String?=null,
    val status:String?=null,
    val otherError:String?=null,
)
