/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.core.data.Constants
import com.fststep.myshop.core.data.Preferences
import com.fststep.myshop.core.view.DashboardActivity
import com.fststep.myshop.databinding.ActivitySplashBinding
import com.fststep.myshop.welcome.view.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Shubham Singh on 13/05/21.
 */
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startApp()
            },
            3000
        )
    }

    private fun startApp() {
        if (preferences.fetchBoolean(Constants.IS_LOGGED_IN, false) && preferences.fetchUserAccount() != null) {
            val serviceType = preferences.fetchUserAccount()?.serviceType
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra(Constants.KEY_SERVICE_TYPE, serviceType)
            startActivity(intent)
            finish()
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}
