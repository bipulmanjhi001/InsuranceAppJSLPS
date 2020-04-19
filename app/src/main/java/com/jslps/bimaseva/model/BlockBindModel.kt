package com.jslps.bimaseva.model

class BlockBindModel {
    var districtCode: String? = null
        private set
    var districtname: String? = null
        private set
    var districtnameh: String? = null
        private set
    var blockname: String? = null
        private set
    var blockcode: String? = null
        private set

    override fun toString(): String {
        return blockname.toString() // What to display in the Spinner list.
    }
}