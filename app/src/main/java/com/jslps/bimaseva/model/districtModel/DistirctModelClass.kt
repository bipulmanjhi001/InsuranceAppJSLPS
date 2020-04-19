package com.jslps.bimaseva.model.districtModel

import com.google.gson.annotations.SerializedName

data class DistirctModelClass (
    @SerializedName("Master") val master : ArrayList<DistrictMasterClass>)
