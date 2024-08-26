/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.bookings.model

/**
 * Created by Shubham Singh on 11/07/21.
 */
data class Booking(
    var id: Int,
    var name: String,
    var date: String,
    var time: String,
    var contact: String,
    var email: String
) {

    fun getTimeVal(): String {
        return time.split(" ")[0]
    }
}
