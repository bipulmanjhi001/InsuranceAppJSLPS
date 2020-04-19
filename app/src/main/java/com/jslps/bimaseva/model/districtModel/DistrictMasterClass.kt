package com.jslps.bimaseva.model.districtModel

import com.google.gson.annotations.SerializedName

data class DistrictMasterClass (
    @SerializedName("DistrictCode") var districtCode : String,
    @SerializedName("DistrictName") var districtName : String,
    @SerializedName("DistrictName_H") var DistrictName_H: String

)