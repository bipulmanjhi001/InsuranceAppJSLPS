package com.jslps.bimaseva.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface LoginServiceNew {
    @GET("TabletDownloadDataInsurance")
    fun getTabletDownloadDataBCsakhi(
        @Query("flag") flag: String, @Query("username") whr1: String , @Query("password") whr2: String): Call<String>

}