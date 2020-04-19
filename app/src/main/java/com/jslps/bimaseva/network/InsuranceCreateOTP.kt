package com.jslps.bimaseva.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface InsuranceCreateOTP {
    @GET("getOTPforCallCenter")
    fun createInsurance(
        @Query("contactNo") flag: String): Call<String>
}