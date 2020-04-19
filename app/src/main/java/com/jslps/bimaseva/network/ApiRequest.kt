package com.twidpay.beta.model

import android.content.Context

class ApiRequest {
    internal var initiator: Int = 0
    internal var requestType: Int = 0
    internal lateinit var apiRequestData: Any
    internal var apiResponseData: Any? = null
    internal var isBackgroundCall: Boolean = false
    internal var context: Context? = null
    fun ApiRequest(requestType: Int) {
        this.requestType = requestType
    }

    // with param
    fun ApiRequest(initiator: Int, requestType: Int, param: Any) {
        this.initiator = initiator
        this.requestType = requestType
        this.apiRequestData = param
    }



    // for No data input
    internal fun OliveRequest(initiator: Int, requestType: Int) {
        this.initiator = initiator
        this.requestType = requestType
    }



    fun getInitiator(): Int {
        return initiator
    }

    fun setInitiator(initiator: Int) {
        this.initiator = initiator
    }

    fun getRequestType(): Int {
        return requestType
    }

    fun setRequestType(requestType: Int) {
        this.requestType = requestType
    }

    fun getRequestData(): Any {
        return apiRequestData
    }

    fun setRequestData(requestData: Any) {
        this.apiRequestData = requestData
    }

}