package com.jslps.bimaseva.network

import android.app.Activity
import android.content.Context
import com.jslps.bimaseva.base.BaseInterface
import com.twidpay.beta.model.ApiRequest

class ServiceUpdateListner : BaseInterface {

    override fun onFailureResponse(request: ApiRequest, data: Any) {
        notification?.onFailureResponse(request, data)
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {
    }


    private var context: Activity? = null
    internal var manager: TransportManager? = null
    internal var notification: BaseInterface? = null

    companion object {
        private var updateManager: ServiceUpdateListner? = null

        fun getInstance(): ServiceUpdateListner {
            if (updateManager == null)
                updateManager = ServiceUpdateListner()
            return updateManager as ServiceUpdateListner
        }

        fun getInstance(context: Activity): ServiceUpdateListner {
            if (updateManager == null)
                updateManager = ServiceUpdateListner()
            updateManager?.setContext(context)
            return updateManager as ServiceUpdateListner
        }
    }


    fun setListener(listener: BaseInterface) {
        this.notification = listener
        getUpiManager().setListener(this)
    }

    fun setContext(context: Activity) {
        this.context = context
    }

    override fun onSuccessResponse(request: ApiRequest, data: Any) {
        notification?.onSuccessResponse(request, data)

    }

    fun passData(request: ApiRequest) {
        getUpiManager(context).passData(request)
    }


    private fun getUpiManager(context: Context?): TransportManager {
        manager = TransportManager.getInstance(this, context)
        return manager as TransportManager
    }

    private fun getUpiManager(): TransportManager {
        manager = TransportManager.getInstance(this, context)
        return manager as TransportManager
    }
}