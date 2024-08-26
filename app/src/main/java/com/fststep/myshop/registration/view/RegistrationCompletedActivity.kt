/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.core.data.Constants
import com.fststep.myshop.core.data.Preferences
import com.fststep.myshop.core.view.DashboardActivity
import com.fststep.myshop.databinding.ActivityRegistrationCompletedBinding
import com.fststep.myshop.registration.model.ServiceType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Shubham Singh on 01/06/21.
 */
@AndroidEntryPoint
class RegistrationCompletedActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegistrationCompletedBinding

    private var isSharingDone: Boolean = false

    @Inject
    lateinit var preferences: Preferences

    lateinit var serviceType: ServiceType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration_completed)

        preferences.fetchTempUserAccount()?.let {
            preferences.storeUserAccount(it)
            serviceType = ServiceType.fromInt(it.serviceType)
            preferences.storeBoolean(true, Constants.IS_LOGGED_IN)

            preferences.clearTempUserAccount()

            mBinding.buttonShare.setOnClickListener {

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    if(serviceType == ServiceType.WINE) {
                        putExtra(Intent.EXTRA_TEXT, "Follow this link to join my WhatsApp group https://chat.whatsapp.com/FQMUQR5d3FIKG8UMTWxT85")
                    } else {
                        putExtra(Intent.EXTRA_TEXT, "Dear Customer,We are happy to announce that " + preferences.fetchUserAccount()?.businessName.toString() + " is now available Online!Download the below app to give us order online" + "# shop google link#")
                    }
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Share your shop")
                startActivity(shareIntent)

                isSharingDone = true
            }
        }

        mBinding.toolbar.btBack.setOnClickListener {
            onBackPressed()
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
