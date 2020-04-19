package com.jslps.bimaseva.ui.home

import android.app.Activity
import com.jslps.bimaseva.base.BasePresenter
import com.jslps.bimaseva.base.Presenter
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.network.ServiceUpdateListner
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.model.Master
import com.twidpay.beta.model.ApiRequest


class HomePresenter(view: HomeView, context: Activity) : BasePresenter, Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {

        getAppCache().insurancetype = data.toString()
        when {
            data.toString() == "PMSBY" ->{
                getAppCache().insuranceStepSend = "1"
                view?.gotoScreen(Constant.SCHEME_DETAILS_FRAGMENT, data)
            }
            data.toString() == "PMJJBY" ->{
                getAppCache().insuranceStepSend = "2"
                view?.gotoScreen(Constant.SCHEME_DETAILS_FRAGMENT, data)
            }
            data.toString() == "PMJAY" ->{
                getAppCache().insuranceStepSend = "3"
                view?.gotoScreen(Constant.SCHEME_DETAILS_FRAGMENT, data)
            }
            data.toString() == "Asset Insurance (Crop, Livestock, Others)" ->{
                getAppCache().insuranceStepSend = "4"
                view?.gotoScreen(Constant.SCHEME_DETAILS_FRAGMENT, data)
            }
            data.toString() == "Registered A New Claim"-> view?.gotoScreen(Constant.INSURANCE_CREATE_INSIDE, data)
        }
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: HomeView? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }

    override fun resume(insuranceName: List<Master>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun resume() {
        val schemedata = ArrayList<String>()
        schemedata.add("Registered A New Claim")
        schemedata.add("PMSBY")
        schemedata.add("PMJJBY")
        schemedata.add("PMJAY")
        schemedata.add("Asset Insurance (Crop, Livestock, Others)")
        view?.loadData(schemedata)
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

