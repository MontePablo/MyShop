/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivitySubscriptionBinding

/**
 * Created by Shubham Singh on 01/06/21.
 */
class SubscriptionActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySubscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_subscription)

        mBinding.toolbar.btBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.tvSkip.setOnClickListener {
            startActivity(Intent(this, PaymentGatewayActivity::class.java))
        }

        mBinding.buttonPay.setOnClickListener {
            startActivity(Intent(this, PaymentGatewayActivity::class.java))
        }
    }
}
