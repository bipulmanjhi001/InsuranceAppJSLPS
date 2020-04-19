package com.jslps.bimaseva.ui.scheme

import com.jslps.bimaseva.base.BaseView

interface SchemeDetailsView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<String>?)
    fun showMessage(message:String)
}