package com.jslps.bimaseva.network


import retrofit2.Call
import retrofit2.http.*

interface UploadRegisterDocumentFalse {
    @POST("Upload_CallCenter_IsStatus")
    @FormUrlEncoded
    fun uploadRegisterDocumentFalse(@Field("sData") flag: String): Call<String>
}