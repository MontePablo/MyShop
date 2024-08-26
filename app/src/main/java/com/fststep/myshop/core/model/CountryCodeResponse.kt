package com.fststep.myshop.core.model

import com.fststep.myshop.core.model.Country

data class CountryCodeResponse (
    val timestamp: String? = null,
    val data: List<Country>? = null,
    val errors: Any? = null
)
