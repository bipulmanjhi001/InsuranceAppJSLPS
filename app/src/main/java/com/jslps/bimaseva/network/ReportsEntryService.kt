package com.jslps.bimaseva.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ReportsEntryService {
    @GET("DownloadInsuranceRpt")
    fun getReportsEntryService(
        @Query("flag") flag: String,@Query("whr")whr:String): Call<String>

}