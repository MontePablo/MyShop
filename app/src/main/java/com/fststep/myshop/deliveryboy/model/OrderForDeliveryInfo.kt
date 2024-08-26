/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.model

/**
 * Created by Jay Kulshreshtha on 16/05/21.
 */
data class OrderForDeliveryInfo(
    var deliveryaddress: String,
    var deliverytype: String,
    var paymenttype: String,
    var deliverystatus: String
)
