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
import com.fststep.myshop.core.data.Constants
import com.fststep.myshop.core.data.Preferences
import com.fststep.myshop.core.view.DashboardActivity
import com.fststep.myshop.databinding.ActivityLoginBinding
import com.fststep.myshop.registration.model.ServiceType
import com.fststep.myshop.registration.model.UserAccount
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Jay Kulshreshtha on 30/05/21.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        mBinding.tvResetPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordOTPActivity::class.java))
            finish()
        }

        mBinding.buttonLogin.setOnClickListener {
            val dummyUser = UserAccount(
                id = 0,
                name = "Shyam Kumar",
                mobileNumber = "9456875214",
                businessName = "Shyam Salon",
                businessCategory = "Beauty Care",
                typeOfBusiness = "Salon",
                serviceType = ServiceType.GOODS_DELIVERY.value,
                deliveryType = null,
                servicePlace = "Shop",
                area = 2.0,
                password = "123456",
            )
            preferences.storeUserAccount(dummyUser)
            preferences.clearTempUserAccount()
            preferences.storeBoolean(true, Constants.IS_LOGGED_IN)
            val serviceType = preferences.fetchUserAccount()?.serviceType
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra(Constants.KEY_SERVICE_TYPE, serviceType)
            startActivity(intent)
            finishAffinity()
        }
    }
}
