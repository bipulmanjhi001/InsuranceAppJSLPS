package com.jslps.bimaseva.model

class HeaderData(isLogoRequired: Boolean, text: String) {
    var isLogoRequired: Boolean = false
        internal set
    var text: String
        internal set

    init {
        this.isLogoRequired = isLogoRequired
        this.text = text
    }
}

