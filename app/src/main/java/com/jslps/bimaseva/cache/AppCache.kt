package com.jslps.bimaseva.cache

import BaseClass
import com.jslps.bimaseva.model.LoginPojo
import com.jslps.bimaseva.model.LoginPojoNew

class AppCache {
    var loginPojo: LoginPojo? = null
    var loginPojonew: BaseClass? = null
    var insurancetype: String? = null
    var insuranceStep: String? = null
    var insuranceOtp: String? = null
    var insuranceStepSend: String? = null
    var schemeID: String? = null

    companion object {
        private var cache: AppCache? = null
        fun getCache(): AppCache {
            if (cache == null) cache = AppCache()
            return cache as AppCache
        }

    }

    fun clearCahe() {
        loginPojo = null
    }


}