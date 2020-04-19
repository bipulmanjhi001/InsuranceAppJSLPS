package com.jslps.bimaseva.base

import android.app.Activity
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.Constant
import com.twidpay.beta.cache.PrefManager


open class Presenter {
    init {

    }

    fun getPref(activity: Activity): PrefManager {
        return PrefManager.getInstance(activity)
    }

    fun getAppCache(): AppCache {
        return AppCache.getCache()
    }
    fun chkIntenrnetIssue(data: Any?): Boolean {
        if (data != null) {
            val internet = data as String
            if (internet.equals(Constant.NO_INTERNET))
                return true
        }
        return false

    }


}