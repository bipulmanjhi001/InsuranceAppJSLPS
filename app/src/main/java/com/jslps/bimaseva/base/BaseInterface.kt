package com.jslps.bimaseva.base

import com.twidpay.beta.model.ApiRequest


interface BaseInterface {
    fun onSuccessResponse(request: ApiRequest, data: Any)
    fun onFailureResponse(request: ApiRequest, data: Any)
    fun onFragmentInteraction(fragmentId: Int, data: Any?)
}