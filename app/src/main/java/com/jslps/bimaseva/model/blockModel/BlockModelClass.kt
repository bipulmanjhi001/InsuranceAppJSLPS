package com.jslps.bimaseva.model.blockModel

import com.google.gson.annotations.SerializedName

data class BlockModelClass (
    @SerializedName("Master") val master : ArrayList<BlockMasterClass>)
