/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.extension

import android.content.res.Resources.getSystem
import android.graphics.BitmapFactory
import androidx.appcompat.widget.AppCompatImageView
import com.fststep.myshop.core.util.Utility
import java.text.DateFormatSymbols

/**
 * Created by Shubham Singh on 05/06/21.
 */

suspend fun AppCompatImageView.setImageFromPath(path: String) {
    if (path.isEmpty()) return
    BitmapFactory.decodeFile(path, Utility.getBitmapOptions())?.also { bitmap ->
        this.setImageBitmap(bitmap)
    }
}

val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

fun String.formatDisplayDate(): String {
    val date = this.dropLast(6)
    val dateSplit = date.split(" ")

    return if (dateSplit[0].toInt() in 11..13) {
        dateSplit[0] + "th " + dateSplit[1]
    } else when (dateSplit[0].toInt() % 10) {
        1 -> dateSplit[0] + "st " + dateSplit[1]
        2 -> dateSplit[0] + "nd " + dateSplit[1]
        3 -> dateSplit[0] + "rd " + dateSplit[1]
        else -> dateSplit[0] + "th " + dateSplit[1]
    }
}

fun String.formatDisplayDateFromServer(): String {
    val date = this.dropLast(5)
    val dateSplit = date.split("/")

    val monthString = DateFormatSymbols().months[dateSplit[1].toInt() - 1]

    return if (dateSplit[0].toInt() in 11..13) {
        dateSplit[0] + "th " + monthString
    } else when (dateSplit[0].toInt() % 10) {
        1 -> dateSplit[0] + "st " + monthString
        2 -> dateSplit[0] + "nd " + monthString
        3 -> dateSplit[0] + "rd " + monthString
        else -> dateSplit[0] + "th " + monthString
    }
}
