/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.registration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fststep.myshop.core.di.interactors.MerchantRegistrationUseCase
import com.fststep.myshop.core.model.CommonResponse
import com.fststep.myshop.core.model.MerchantRegistrationRequest
import com.fststep.myshop.core.retrofit.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Shubham Singh on 29/08/21.
 */
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val merchantRegistrationUseCase: MerchantRegistrationUseCase,
) : ViewModel() {

    var registrationResponse: MutableLiveData<State<CommonResponse>> = MutableLiveData()

    fun registerUser(userData: MerchantRegistrationRequest) {
        viewModelScope.launch {
            merchantRegistrationUseCase(userData).collect {
                registrationResponse.value = it
            }
        }
    }
}
