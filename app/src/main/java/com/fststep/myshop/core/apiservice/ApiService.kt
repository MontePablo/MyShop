/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.core.apiservice


import com.fststep.myshop.core.model.CommonResponse
import com.fststep.myshop.core.model.CountryCodeResponse
import com.fststep.myshop.core.model.LoginRequest
import com.fststep.myshop.core.model.LoginResponse
import com.fststep.myshop.core.model.MerchantRegistrationRequest
import com.fststep.myshop.core.model.TermsResponse
import com.fststep.nxt2me.core.data.models.CategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("fst-registration/data/api/category/all")
    suspend fun getCategories(): CategoryResponse
    @GET("fst-registration/data/api/country/all")
    suspend fun getCountryCode(): CountryCodeResponse
    @GET("fst-registration/data/api/termsCondition/all")
    suspend fun getTerms(): TermsResponse
    @PUT("fst-registration/api/updateTerms/{userType}/{id}")
    suspend fun acceptTerms(@Header("Authorization") token:String,@Path("userType") userType:String,@Path("id") id:String): CommonResponse
    @POST("fst-registration/api/merchant")
    suspend fun registerMerchant(@Body request: MerchantRegistrationRequest): CommonResponse

    @POST("merchant/login")
    suspend fun performLogin(@Body request: LoginRequest): LoginResponse

}
