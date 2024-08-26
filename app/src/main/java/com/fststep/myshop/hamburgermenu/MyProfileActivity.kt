/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.hamburgermenu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.core.data.Constants
import com.fststep.myshop.core.data.Preferences
import com.fststep.myshop.core.view.DashboardActivity
import com.fststep.myshop.databinding.ActivityMyProfileBinding
import javax.inject.Inject

/**
 * Created by Jay Kulshreshtha on 29/05/21.
 */
class MyProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMyProfileBinding

    private var isSharingDone: Boolean = false

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        preferences = Preferences(this)

        mBinding.ivShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Dear Customer,We are happy to announce that " + preferences.fetchUserAccount()?.businessName.toString() + " is now available Online! Download the below app and use my referral code - " + mBinding.tvReferralCodeValue.text.toString() + "for registration." + "# My Shop google link#")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Share your shop")
            startActivity(shareIntent)

            isSharingDone = true
        }
    }

    override fun onResume() {
        super.onResume()

        if (isSharingDone) {
            val serviceType = preferences.fetchUserAccount()?.serviceType
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra(Constants.KEY_SERVICE_TYPE, serviceType)
            startActivity(intent)
            finishAffinity()
        }
    }

}
