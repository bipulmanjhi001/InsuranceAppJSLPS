package com.jslps.bimaseva.network

import android.content.Context
import android.net.ConnectivityManager
import com.jslps.bimaseva.base.BaseInterface
import com.jslps.bimaseva.Constant
import com.twidpay.beta.model.ApiRequest
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Interceptor
import okhttp3.Response
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TransportManager {
    private var listener: BaseInterface? = null
    //    var disposable: Disposable? = null
    private fun isConnectionAvailable(context: Context?): Boolean {
        if (context == null) return false
        val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun setListener(listener: BaseInterface) {
        this.listener = listener
    }

    private val mCompositeDisposable = CompositeDisposable()

    fun destroy() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear()
    }

    companion object {
        //private var apiServices: NetworkService? = null
        private var manager: TransportManager? = null
        private var context: Context? = null
        fun getInstance(conlistener: BaseInterface, context: Context?): TransportManager {
            if (manager == null) manager = TransportManager()
            manager!!.setListener(conlistener)
            this.context = context
            return manager as TransportManager
        }

        fun getInstance(conlistener: BaseInterface): TransportManager {
            if (manager == null) manager = TransportManager()
            manager!!.setListener(conlistener)
            return manager as TransportManager
        }

        val header = Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            builder.method(original.method(), original.body())
            chain.proceed(builder.build())
        }




    }

    fun passData(request: ApiRequest?) {
        if (request == null) return
        if (isConnectionAvailable(request.context)) {
            when (request.requestType) {

            }

        } else {
            onFailure(request, Constant.NO_INTERNET)
            // listener?.onFailureResponse(request, Constants.NO_INTERNET)
        }
    }


    private fun onFailure(request: ApiRequest, data: String) {
        val response = ResultResponse<Void>()
        if (data.equals(Constant.NO_INTERNET, true))
            response.message = data
        else
            response.message = "Unable to process your request"
        response.data = null
        response.error_code = "-99"
        listener?.onFailureResponse(request, response)
    }


    private fun <T> subscribe(observable: Observable<T>, observer: Observer<T>) {
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun processCondition(request: ApiRequest, result: ResultResponse<*>) {

        if (result.error_code.equals("1")) {
            listener?.onSuccessResponse(request, result)
        } else {
            listener?.onFailureResponse(request, result)
        }
    }
}

//    private fun sendFailureResponse(requestObject: ApiRequest?) {
//        if (requestObject != null && listener != null) {
//            //            Log.d("TAG", "sendFailureResponse: " + requestObject.getReqType());
//            listener?.onFailureResponse(requestObject, "any")
//        }
//    }


