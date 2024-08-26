package com.fststep.myshop.core.model

data class JwtTokenPayload (
    val jti: String? = null,
    val iat: Long? = null,
    val exp: Long? = null
)