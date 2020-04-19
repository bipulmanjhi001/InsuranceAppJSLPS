package com.jslps.bimaseva.ui.reports

import com.jslps.bimaseva.base.BaseView
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.model.MasterX

interface SchemeDetailsViewReport : BaseView {
    fun gotoScreen(fragmentID: Int, message: Any?)
    fun loadData(cardInitResponse: ArrayList<MasterX>)
    fun loadDataReport(cardInitResponse: ArrayList<Master>)
    fun showMessage(message:String)
}