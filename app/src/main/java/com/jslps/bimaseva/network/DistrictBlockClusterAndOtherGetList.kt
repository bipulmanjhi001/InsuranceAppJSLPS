package com.jslps.bimaseva.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DistrictBlockClusterAndOtherGetList {
    @GET("TabletDownloadDataDistrictCBS")
    fun fetchDistrictBlockClusterAndOtherGetList(
        @Query("whr") whr: String, @Query("flag") flag: String
        , @Query("whr1") whr1: String, @Query("whr2") whr2: String
    ): Call<String>
}