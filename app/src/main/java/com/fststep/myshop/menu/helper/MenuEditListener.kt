/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.menu.helper

import com.fststep.myshop.menu.model.Product

/**
 * Created by Shubham Singh on 27/06/21.
 */
interface MenuEditListener {
    fun onItemRemove(index: Int)
    fun onItemEdit(index: Int)
    fun onItemUpdate(index: Int, product: Product)
    fun showImagePicker(isEditMode: Boolean = false, setSelectedImage: ((path: String) -> Unit)? = null)
}
