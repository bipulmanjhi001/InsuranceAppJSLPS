package com.jslps.bimaseva.ui.claimSetteled

import com.jslps.bimaseva.base.BaseView
import com.jslps.bimaseva.model.Master

interface ClaimSetteledDetailsView : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<Master>?)
    fun showMessage(message: Any?)
}