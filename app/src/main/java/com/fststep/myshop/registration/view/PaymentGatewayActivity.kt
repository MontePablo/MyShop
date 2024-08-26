/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityPaymentGatewayBinding

/**
 * Created by Shubham Singh on 01/06/21.
 */
class PaymentGatewayActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityPaymentGatewayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment_gateway)

        mBinding.toolbar.btBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        fakeProgress()
    }

    private fun fakeProgress() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                mBinding.progress.visibility = View.VISIBLE
            },
            250
        )

        Handler(Looper.getMainLooper()).postDelayed(
            {
                mBinding.progress.visibility = View.GONE
                startActivity(Intent(this, RegistrationCompletedActivity::class.java))
            },
            1500
        )
    }
}
