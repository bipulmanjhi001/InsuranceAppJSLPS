package com.jslps.bimaseva.network


import retrofit2.Call
import retrofit2.http.*

interface UploadRegisterDocument {
    @POST("InsuranceImages")
    @FormUrlEncoded
    fun uploadRegistedinsurance(@Field("sData") flag: String): Call<String>
}