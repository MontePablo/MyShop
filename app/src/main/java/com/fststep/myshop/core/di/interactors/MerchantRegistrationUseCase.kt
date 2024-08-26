/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.di.interactors

import com.fststep.myshop.core.model.CommonResponse
import com.fststep.myshop.core.model.MerchantRegistrationRequest
import com.fststep.myshop.core.apiservice.ApiService
import com.fststep.myshop.core.retrofit.State
import com.fststep.myshop.core.retrofit.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class MerchantRegistrationUseCase @Inject constructor(private val apiService: ApiService) {

    operator fun invoke(regData: MerchantRegistrationRequest) = flow<State<CommonResponse>> {
        apiService.registerMerchant(regData).run {
            emit(State.DataState(this))
        }
    }.catch {
        it.printStackTrace()
        emit(NetworkUtil.resolveError(it))
    }.onStart {
        emit(State.LoadingState(true))
    }.onCompletion {
        emit(State.LoadingState(false))
    }.flowOn(Dispatchers.IO)
}
