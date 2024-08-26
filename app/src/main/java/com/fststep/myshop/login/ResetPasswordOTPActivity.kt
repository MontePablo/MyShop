/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityResetPasswordOtpBinding

/**
 * Created by Jay Kulshreshtha on 30/05/21.
 */
class ResetPasswordOTPActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityResetPasswordOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password_otp)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        mBinding.buttonDone.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
