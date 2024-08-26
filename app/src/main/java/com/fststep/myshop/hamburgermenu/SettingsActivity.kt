/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.hamburgermenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivitySettingsBinding
import com.fststep.myshop.login.ResetPasswordOTPActivity

/**
 * Created by Jay Kulshreshtha on 29/05/21.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        mBinding.ivShopStatusActive.setOnClickListener {
            mBinding.ivShopStatusActive.visibility = View.GONE
            mBinding.ivShopStatusDeactive.visibility = View.VISIBLE
        }

        mBinding.ivShopStatusDeactive.setOnClickListener {
            mBinding.ivShopStatusActive.visibility = View.VISIBLE
            mBinding.ivShopStatusDeactive.visibility = View.GONE
        }

        mBinding.tvResetPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordOTPActivity::class.java))
            finish()
        }
    }
}
