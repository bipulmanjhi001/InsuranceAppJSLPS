package com.jslps.bimaseva.base

import com.jslps.bimaseva.model.Master

interface BasePresenter : BaseInterface {
    fun init()
    fun resume(insuranceName: List<Master>?)
    fun resume()
    fun onDestroy()
    fun onPause()
}