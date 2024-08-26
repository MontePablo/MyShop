package com.fststep.myshop.core.model

data class MerchantRegistrationRequest (
    val area: Long? = null,
    val businessName: String? = null,
    val categoryID: Long? = null,
    val categoryTypeMapping: Long? = null,
    val countryID: Long? = null,
    val emailID: String? = null,
    val latitude: Long? = null,
    val longitude: Long? = null,
    val merchantName: String? = null,
    val mobileNo: String? = null,
    val password: String? = null,
    val referrerReferralCode: String? = null,
    val subCategoryID: Long? = null
)
