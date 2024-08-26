package com.fststep.myshop.hamburgermenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityMyRewardsBinding

class MyRewardsActivity: AppCompatActivity() {

    private lateinit var mBinding:ActivityMyRewardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_rewards)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        mBinding.btnRedeem.setOnClickListener {

        }

        mBinding.btnDetails.setOnClickListener {

        }
    }

}