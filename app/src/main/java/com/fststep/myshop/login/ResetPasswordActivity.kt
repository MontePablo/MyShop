/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityResetPasswordBinding
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Jay Kulshreshtha on 30/05/21.
 */
class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        mBinding.buttonConfirm.setOnClickListener {
            if (isValid()) {
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
    }

    private fun isValid(): Boolean {
        cleanError()
        var result = true
        when {
            mBinding.etNewPassword.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlNewPassword.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlNewPassword)
            }

            mBinding.etConfirmPassword.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlConfirmPassword.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlConfirmPassword)
            }

            mBinding.etNewPassword.text?.trim() != mBinding.etConfirmPassword.text?.trim() -> {
                result = false
                mBinding.tlNewPassword.error = getString(R.string.lbl_password_mismatch)
                mBinding.tlConfirmPassword.error = getString(R.string.lbl_password_mismatch)
                highlightErrorField(mBinding.tlNewPassword)
            }
        }
        return result
    }

    private fun cleanError() {
        mBinding.tlNewPassword.error = null
        mBinding.tlConfirmPassword.error = null
    }

    private fun highlightErrorField(view: TextInputLayout) {
        view.editText?.requestFocus()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                cleanError()
            },
            1500
        )
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
