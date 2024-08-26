/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.core.data.Preferences
import com.fststep.myshop.core.extension.observe
import com.fststep.myshop.core.model.CommonResponse
import com.fststep.myshop.core.model.MerchantRegistrationRequest
import com.fststep.myshop.core.retrofit.State
import com.fststep.myshop.core.util.Utility
import com.fststep.myshop.databinding.ActivityNewRegistrationBinding
import com.fststep.myshop.registration.helper.CategoryList
import com.fststep.myshop.registration.model.ServiceType
import com.fststep.myshop.registration.model.UserAccount
import com.fststep.myshop.registration.viewmodel.RegistrationViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Shubham Singh on 13/05/21.
 */
@AndroidEntryPoint
class NewRegistrationActivity : AppCompatActivity() {
    private val registrationViewModel:RegistrationViewModel by viewModels()
    private lateinit var mBinding: ActivityNewRegistrationBinding

    @Inject
    lateinit var preferences: Preferences

    private var selectedBusinessCategory: Int = 0
    private var selectedTypeOfBusiness: Int = 0
    private var selectedDelivery: Int = 0
    private var selectedServicePlacePhone: Int = 0
    private var selectedServicePlaceBooking: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_registration)

        registrationViewModel.apply {
            observe(registrationResponse,::onRegistration)
        }



        ArrayAdapter(
            this,
            R.layout.spinner_item,
            CategoryList.getCategoryList()
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            mBinding.spinnerBusinessCategory.adapter = adapter
        }

        ArrayAdapter(
            this,
            R.layout.spinner_item,
            CategoryList.getSubCategoryList(selectedBusinessCategory)
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            mBinding.spinnerTypeOfBusiness.adapter = adapter
        }

        ArrayAdapter(
            this,
            R.layout.spinner_item,
            resources.getStringArray(R.array.phone_entries)
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            mBinding.spinnerServicePlacePhone.adapter = adapter
        }

        ArrayAdapter(
            this,
            R.layout.spinner_item,
            resources.getStringArray(R.array.booking_entries)
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            mBinding.spinnerServicePlaceBooking.adapter = adapter
        }

        ArrayAdapter(
            this,
            R.layout.spinner_item,
            resources.getStringArray(R.array.good_delivery_entries)
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            mBinding.spinnerDelivery.adapter = adapter
        }

        updateServiceTypeSpinner(CategoryList.getServiceType(selectedBusinessCategory, selectedTypeOfBusiness))

        mBinding.spinnerBusinessCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedBusinessCategory = position
                selectedTypeOfBusiness = 0
                ArrayAdapter(
                    this@NewRegistrationActivity,
                    R.layout.spinner_item,
                    CategoryList.getSubCategoryList(selectedBusinessCategory)
                ).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    mBinding.spinnerTypeOfBusiness.adapter = adapter
                }

                updateServiceTypeSpinner(CategoryList.getServiceType(selectedBusinessCategory, selectedTypeOfBusiness))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        mBinding.spinnerTypeOfBusiness.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTypeOfBusiness = position

                updateServiceTypeSpinner(CategoryList.getServiceType(selectedBusinessCategory, selectedTypeOfBusiness))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        mBinding.spinnerDelivery.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDelivery = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        mBinding.spinnerServicePlacePhone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedServicePlacePhone = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        mBinding.spinnerServicePlaceBooking.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedServicePlaceBooking = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        mBinding.tlArea.setEndIconOnClickListener {
            Toast.makeText(this, "You can define the area in which you agree to deliver the order. Also, your advertisement will be shown to this defined area.", Toast.LENGTH_LONG).show()
        }

        mBinding.buttonNext.setOnClickListener {
            if (isValid()) {
                createMerchant()
            }
        }
    }

    private fun onRegistration(state: State<CommonResponse>?) {
        when (state) {
//            is State.LoadingState -> {
//                Log.d(this.localClassName,"state.loading"+state.isLoading.toString())
//            }
            is State.ErrorState -> {
                Utility.performErrorState(this,state, getString(R.string.signup_failed))
            }

            is State.DataState -> {
                Toast.makeText(applicationContext,
                    getString(R.string.success),Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, TermsActivity::class.java))
                finishAffinity()
            }
            else -> {}
        }

    }

    private fun createMerchant() {
        val newUser = MerchantRegistrationRequest(
            id = 0,
            name = mBinding.etName.text?.trim().toString(),
            mobileNumber = mBinding.etMobileNumber.text?.trim().toString(),
            businessName = mBinding.etBusinessName.text?.trim().toString(),
            businessCategory = CategoryList.getBusinessCategory(selectedBusinessCategory),
            typeOfBusiness = CategoryList.getTypeOfBusiness(selectedBusinessCategory, selectedTypeOfBusiness),
            deliveryType = getDeliveryValue(),
            servicePlace = getServicePlaceValue(),
            serviceType = CategoryList.getServiceType(selectedBusinessCategory, selectedTypeOfBusiness).value,
            area = mBinding.etArea.text?.trim().toString().toDouble(),
            password = mBinding.etPassword.text?.trim().toString(),
            referralCode = mBinding.etReferralCode.text?.trim().toString()
        )
    }

    private fun getDeliveryValue(): String? {
        return if (CategoryList.getServiceType(selectedBusinessCategory, selectedTypeOfBusiness) == ServiceType.GOODS_DELIVERY)
            resources.getStringArray(R.array.good_delivery_entries).getOrNull(selectedDelivery)
        else
            null
    }

    private fun getServicePlaceValue(): String? {
        return if (CategoryList.getServiceType(selectedBusinessCategory, selectedTypeOfBusiness) == ServiceType.BOOKING)
            resources.getStringArray(R.array.booking_entries).getOrNull(selectedServicePlaceBooking)
        else if (CategoryList.getServiceType(selectedBusinessCategory, selectedTypeOfBusiness) == ServiceType.PHONE)
            resources.getStringArray(R.array.phone_entries).getOrNull(selectedServicePlacePhone)
        else
            null
    }

    private fun isValid(): Boolean {
        cleanError()
        var result = true
        when {
            mBinding.etName.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlName.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlName)
            }

            mBinding.etMobileNumber.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlMobileNumber.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlMobileNumber)
            }

            mBinding.etMobileNumber.text?.trim()?.length != 10 -> {
                result = false
                mBinding.tlMobileNumber.error = getString(R.string.lbl_invalid)
                highlightErrorField(mBinding.tlMobileNumber)
            }

            mBinding.etBusinessName.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlBusinessName.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlBusinessName)
            }

            mBinding.etArea.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlArea.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlArea)
            }

            mBinding.etPassword.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlPassword.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlPassword)
            }

            mBinding.etConfirmPassword.text?.trim().isNullOrEmpty() -> {
                result = false
                mBinding.tlConfirmPassword.error = getString(R.string.lbl_required)
                highlightErrorField(mBinding.tlConfirmPassword)
            }

            mBinding.etPassword.text?.trim() != mBinding.etConfirmPassword.text?.trim() -> {
                result = false
                mBinding.tlPassword.error = getString(R.string.lbl_password_mismatch)
                mBinding.tlConfirmPassword.error = getString(R.string.lbl_password_mismatch)
                highlightErrorField(mBinding.tlPassword)
            }
        }
        return result
    }

    private fun highlightErrorField(view: TextInputLayout) {
        view.editText?.requestFocus()
        mBinding.parentScrollView.post {
            mBinding.parentScrollView.smoothScrollTo(0, view.top)
        }
        Handler(Looper.getMainLooper()).postDelayed(
            {
                cleanError()
            },
            1500
        )
    }

    private fun cleanError() {
        mBinding.tlName.error = null
        mBinding.tlMobileNumber.error = null
        mBinding.tlBusinessName.error = null
        mBinding.tlArea.error = null
        mBinding.tlPassword.error = null
        mBinding.tlConfirmPassword.error = null
    }

    private fun updateServiceTypeSpinner(type: ServiceType) {
        mBinding.serviceType = type
    }
}
