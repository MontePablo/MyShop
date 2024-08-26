package com.fststep.myshop.core.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityGenerateCouponsBinding

/**
 * Created by Shubham Singh on 09/08/21.
 */
class GenerateCouponsActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivityGenerateCouponsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_generate_coupons)

        mBinding.toolbar.btBack.setOnClickListener {
            onBackPressed()
        }
    }
}