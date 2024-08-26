package com.fststep.myshop.advertisement

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityAdvertisementBinding
import com.fststep.myshop.promotions.BigBannerAdvertisementActivity
import com.fststep.myshop.promotions.LaunchNewProductActivity
import com.fststep.myshop.promotions.SmallBannerAdvertisementActivity
import java.text.SimpleDateFormat
import java.util.*


class AdvertisementActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivityAdvertisementBinding
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_advertisement)

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        val status = intent.extras?.getBoolean("advertisement_status")
        if (status == true) {
            mBinding.clSubscribed.visibility = View.VISIBLE
            mBinding.clNoSubscription.visibility = View.GONE
        } else {
            mBinding.clSubscribed.visibility = View.GONE
            mBinding.clNoSubscription.visibility = View.VISIBLE
        }

        mBinding.clBigBannerAdvertisement.setOnClickListener {
            startActivity(Intent(this, BigBannerAdvertisementActivity::class.java))
        }

        mBinding.clSmallBannerAdvertisement.setOnClickListener {
            startActivity(Intent(this, SmallBannerAdvertisementActivity::class.java))
        }

        mBinding.clLaunchNewProduct.setOnClickListener {
           startActivity(Intent(this, LaunchNewProductActivity::class.java))
        }

        val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = monthOfYear
            calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
            }

        mBinding.ivCalendar.setOnClickListener {
            DatePickerDialog(
                this,R.style.DialogTheme, date, calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        mBinding.tvStartDate.text = sdf.format(calendar.time)

        calendar.time = sdf.parse(mBinding.tvStartDate.text.toString())
        calendar.add(Calendar.DATE,7)
        mBinding.tvEndDate.text = sdf.format(calendar.time)
    }

}