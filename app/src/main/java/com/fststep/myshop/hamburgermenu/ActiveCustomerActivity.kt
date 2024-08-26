package com.fststep.myshop.hamburgermenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityActiveCustomerBinding

class ActiveCustomerActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivityActiveCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_active_customer)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

}