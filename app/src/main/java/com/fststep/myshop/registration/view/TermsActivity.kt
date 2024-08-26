/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.view

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityTermsBinding

/**
 * Created by Shubham Singh on 16/05/21.
 */
class TermsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_terms)

        mBinding.toolbar.btBack.setOnClickListener {
            onBackPressed()
        }

        val formatedHeading = SpannableString(getString(R.string.tnc_heading))
        formatedHeading.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.tnc_red_text_color)), 10, formatedHeading.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding.tvTncHeading.text = formatedHeading

        mBinding.buttonNext.setOnClickListener {
            startActivity(Intent(this, BankDetailsActivity::class.java))
        }
    }
}
