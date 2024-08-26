/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.hamburgermenu

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View.OnFocusChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityContactUsBinding

/**
 * Created by Jay Kulshreshtha on 29/05/21.
 */
class ContactUsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        mBinding.etEmailAddress.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // code to execute when EditText loses focus
                if (!isValidEmail(mBinding.etEmailAddress.text.toString())) {
                    mBinding.etEmailAddress.error = "Invalid email"
                }
            }
        }
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
