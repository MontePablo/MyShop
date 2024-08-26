/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Shubham Singh on 16/05/21.
 */
@Parcelize
data class SubCategory(
    val subCategoryId: Int,
    val subCategoryName: String,
    val serviceType: ServiceType
) : Parcelable

enum class ServiceType(val value: Int) {
    GOODS_DELIVERY(1),
    PHONE(2),
    BOOKING(3),
    WINE(4),
    NONE(0);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
