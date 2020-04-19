package com.jslps.bimaseva.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WelcomePageReports {
    @GET("Bima_dashboardData")
    fun getWelcomePageReports(
        @Query("whr") whr: String): Call<String>

}