package com.jslps.bimaseva.network




class ResultResponse<T> {
    var message: String = ""
    var error_code: String = ""
    var data: T? = null

}