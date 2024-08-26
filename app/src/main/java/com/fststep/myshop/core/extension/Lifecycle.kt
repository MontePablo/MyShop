/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.fststep.myshop.core.retrofit.exception.Failure

/**
 * Created by Shubham Singh on 13/05/21.
 */

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L?, body: (T?) -> Unit) =
    liveData?.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this, Observer(body))
