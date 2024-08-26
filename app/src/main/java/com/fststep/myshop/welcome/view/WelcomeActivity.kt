/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.welcome.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityWelcomeBinding
import com.fststep.myshop.login.LoginActivity
import com.fststep.myshop.registration.view.NewRegistrationActivity

/**
 * Created by Shubham Singh on 13/05/21.
 */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        mBinding.buttonRegistration.setOnClickListener {
            startActivity(Intent(this, NewRegistrationActivity::class.java))
        }

        mBinding.buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
