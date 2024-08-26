/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.promotions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityLaunchNewProductBinding

/**
 * Created by Jay Kulshreshtha on 5/06/21.
 */
class LaunchNewProductActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLaunchNewProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_launch_new_product)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }
}
