package com.jslps.bimaseva.ui.documentFale

import android.app.Activity
import com.jslps.bimaseva.base.BasePresenter
import com.jslps.bimaseva.base.Presenter
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.network.ServiceUpdateListner
import com.jslps.bimaseva.ui.documentNotReady.DocumentFalseView
import com.twidpay.beta.model.ApiRequest
import java.util.*
import kotlin.collections.ArrayList


class DocumentFalsePresenter(view: DocumentFalseView, context: Activity) : BasePresenter,
    Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {

    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: DocumentFalseView? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }

    override fun resume(insuranceName: List<Master>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun resume() {

    }
    fun loadData(insuranceName: List<Master>?){
        view?.loadData(insuranceName as ArrayList<Master>)
    }
    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onSuccessResponse(request: ApiRequest, data: Any) {

    }

    override fun onFailureResponse(request: ApiRequest, data: Any) {
        view?.hideProgress()
        view?.loadData(null)
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {

    }

    fun getListner(): OnFragmentListItemSelectListener {
        return this
    }


}

