package com.jslps.bimaseva.ui.home

import com.jslps.bimaseva.base.BaseView

interface HomeView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<String>?)
}