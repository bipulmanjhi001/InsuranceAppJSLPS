package com.jslps.bimaseva.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface InsuranceCreate {
    @GET("CallCenter")
    fun createInsurance(
        @Query("jsonstr") flag: String): Call<String>
}