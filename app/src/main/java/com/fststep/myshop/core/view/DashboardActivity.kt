/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.fststep.myshop.R
import com.fststep.myshop.SplashActivity
import com.fststep.myshop.advertisement.AdvertisementActivity
import com.fststep.myshop.core.data.Constants
import com.fststep.myshop.core.data.Preferences
import com.fststep.myshop.core.view.adapter.DashboardViewPagerAdapter
import com.fststep.myshop.core.view.fragment.EarningsFragment
import com.fststep.myshop.core.view.fragment.MenuFragment
import com.fststep.myshop.core.view.fragment.PromotionFragment
import com.fststep.myshop.core.view.fragment.RecentOrderFragment
import com.fststep.myshop.databinding.ActivityDashboardBinding
import com.fststep.myshop.hamburgermenu.*
import com.fststep.myshop.promotions.OwnPromotionActivity
import com.fststep.myshop.registration.model.ServiceType
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Jay Kulshreshtha on 22/05/21.
 */
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDashboardBinding
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private lateinit var dashboardViewPagerAdapter: DashboardViewPagerAdapter
    private lateinit var serviceType: ServiceType
    private var advertismentSubscribed: Boolean = false

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        val serviceTypeVal: Int = intent.getIntExtra(Constants.KEY_SERVICE_TYPE, 0)

        serviceType = ServiceType.fromInt(serviceTypeVal)

        tabLayout = mBinding.tlDashboard
        viewPager = mBinding.vpDashboard
        tabLayout?.setupWithViewPager(viewPager)

        if(serviceType == ServiceType.WINE) {
            mBinding.tlDashboard.visibility = View.GONE
            mBinding.vpDashboard.visibility = View.GONE
            mBinding.layoutWine.visibility = View.VISIBLE

            mBinding.tvWineLink.setOnClickListener {
                val url = "https://chat.whatsapp.com/FQMUQR5d3FIKG8UMTWxT85"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }

        mBinding.ivShopStatusActive.setOnClickListener {
            mBinding.ivShopStatusActive.visibility = View.GONE
            mBinding.ivShopStatusDeactive.visibility = View.VISIBLE
            DeactivateDialog.newInstance().show(supportFragmentManager, DeactivateDialog.TAG)
            mBinding.tvShopStatus.text = getString(R.string.lbl_deactive)
        }

        mBinding.ivShopStatusDeactive.setOnClickListener {
            mBinding.ivShopStatusActive.visibility = View.VISIBLE
            mBinding.ivShopStatusDeactive.visibility = View.GONE
            mBinding.tvShopStatus.text = getString(R.string.lbl_active)
        }

        viewPager?.let { setupViewPager(it) }
        setupTabIcons()

        mBinding.ivHamburger.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.VISIBLE
            mBinding.clHamburgerMenu.visibility = View.VISIBLE
        }

        mBinding.vHamburgerBackground.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
        }

        mBinding.tvPromotion.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            startActivity(Intent(this,OwnPromotionActivity::class.java))
        }

        mBinding.tvMyAdvertisement.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            val intent = Intent(this,AdvertisementActivity::class.java).apply {
                putExtra("advertisement_status",advertismentSubscribed)
            }
            startActivity(intent)
        }

        mBinding.tvMyProfile.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        mBinding.tvRewards.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            startActivity(Intent(this, MyRewardsActivity::class.java))
        }

        mBinding.tvContactUs.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            startActivity(Intent(this, ContactUsActivity::class.java))
        }

        mBinding.tvSettings.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        mBinding.tvActiveCustomer.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            startActivity(Intent(this, ActiveCustomerActivity::class.java))
        }

        mBinding.tvLogout.setOnClickListener {
            mBinding.vHamburgerBackground.visibility = View.GONE
            mBinding.clHamburgerMenu.visibility = View.GONE
            logout()
        }
    }

    private fun setupViewPager(viewpager: ViewPager) {
        if (!::serviceType.isInitialized) return
        dashboardViewPagerAdapter = DashboardViewPagerAdapter(supportFragmentManager)

        when (serviceType) {
            ServiceType.GOODS_DELIVERY -> initGoodsDeliveryFrags()
            ServiceType.BOOKING -> initBookingServiceFrags()
            ServiceType.PHONE -> initPhoneServiceFrags()
            ServiceType.NONE -> return
            else -> {}
        }

        // setting adapter to view pager.
        viewpager.adapter = dashboardViewPagerAdapter
    }

    private fun initGoodsDeliveryFrags() {
        dashboardViewPagerAdapter.addFragment(MenuFragment.newInstance(ServiceType.GOODS_DELIVERY), "Menu")
        dashboardViewPagerAdapter.addFragment(RecentOrderFragment.newInstance(ServiceType.GOODS_DELIVERY), "Recent Order")
        dashboardViewPagerAdapter.addFragment(EarningsFragment.newInstance(ServiceType.GOODS_DELIVERY), "Earnings")
        dashboardViewPagerAdapter.addFragment(PromotionFragment.newInstance(ServiceType.GOODS_DELIVERY), "Promotion")
    }

    private fun initBookingServiceFrags() {
        dashboardViewPagerAdapter.addFragment(MenuFragment.newInstance(ServiceType.BOOKING), "Services")
        dashboardViewPagerAdapter.addFragment(RecentOrderFragment.newInstance(ServiceType.BOOKING), "Appointments")
        dashboardViewPagerAdapter.addFragment(EarningsFragment.newInstance(ServiceType.BOOKING), "Earnings")
        dashboardViewPagerAdapter.addFragment(PromotionFragment.newInstance(ServiceType.BOOKING), "Promotion")
    }

    private fun initPhoneServiceFrags() {
        dashboardViewPagerAdapter.addFragment(MenuFragment.newInstance(ServiceType.PHONE), "Services")
        dashboardViewPagerAdapter.addFragment(RecentOrderFragment.newInstance(ServiceType.PHONE), "People may have reached you")
    }

    private fun setupTabIcons() {
        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun logout() {
        preferences.clearAllPreferences()
        startActivity(Intent(this, SplashActivity::class.java))
        finishAffinity()
    }
}
