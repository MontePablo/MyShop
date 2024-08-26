/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityDeliveryBoyDetailBinding

/**
 * Created by Jay Kulshreshtha on 16/05/21.
 */
class DeliveryBoyDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDeliveryBoyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_boy_detail)

        mBinding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Do Whatever you want in isChecked
                mBinding.buttonIAcknowledge.visibility = View.VISIBLE
            } else {
                mBinding.buttonIAcknowledge.visibility = View.GONE
            }
        }

        mBinding.buttonIAcknowledge.setOnClickListener {
            startActivity(Intent(this, DeliveryStatusActivity::class.java))
        }

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }
}
