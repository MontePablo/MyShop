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
import com.fststep.myshop.databinding.ActivityBankDetailsBinding

/**
 * Created by Shubham Singh on 31/05/21.
 */
class BankDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityBankDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bank_details)

        mBinding.toolbar.btBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.buttonNext.setOnClickListener {
            startActivity(Intent(this, SubscriptionActivity::class.java))
        }

        mBinding.tvSkip.setOnClickListener {
            startActivity(Intent(this, SubscriptionActivity::class.java))
        }
    }
}
