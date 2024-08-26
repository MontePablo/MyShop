/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Shubham Singh on 10/06/21.
 */
@Parcelize
data class UserAccount(
    var id: Int,
    var name: String,
    var mobileNumber: String,
    var businessName: String,
    var businessCategory: String,
    var typeOfBusiness: String,
    var serviceType: Int,
    var deliveryType: String? = null,
    var servicePlace: String? = null,
    var area: Double,
    var password: String,
    var referralCode: String? = null
) : Parcelable
