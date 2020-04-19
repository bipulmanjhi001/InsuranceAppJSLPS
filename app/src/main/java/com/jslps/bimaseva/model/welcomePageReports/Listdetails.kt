package com.jslps.bimaseva.model.welcomePageReports

import com.google.gson.annotations.SerializedName

data class Listdetails (
    @SerializedName("Insurance") val insurance : String,
    @SerializedName("Register") val register : Int,
    @SerializedName("UnderProcess") val underProcess : Int,
    @SerializedName("completed") val completed : Int,
    @SerializedName("Reject") val reject : Int
)