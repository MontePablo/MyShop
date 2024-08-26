/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.model

/**
 * Created by Shubham Singh on 16/05/21.
 */
data class Category(
    val categoryId: Int,
    val categoryName: String,
    val subCategories: ArrayList<SubCategory>
)
