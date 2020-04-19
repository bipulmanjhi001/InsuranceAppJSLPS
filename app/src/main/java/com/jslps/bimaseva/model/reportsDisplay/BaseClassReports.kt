package com.jslps.bimaseva.model.reportsDisplay

import com.google.gson.annotations.SerializedName

data class BaseClassReports (
    @SerializedName("Master") val master : List<MasterReports>

)