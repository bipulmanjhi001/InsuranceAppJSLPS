package com.twidpay.beta.cache

import android.content.Context
import android.content.SharedPreferences


class PrefManager {

    // Context
    lateinit var _context: Context

    // Shared pref mode



    companion object {

        private var instance: PrefManager? = null
        lateinit var pref: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        internal var PRIVATE_MODE = 0
        val PREF_NAME = "insuranceApp"

        fun setEditer(context: Context) {
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            editor = pref.edit()
        }

        fun getInstance(ctx: Context): PrefManager {
            if (instance == null) instance = PrefManager()
            setEditer(ctx)
            return instance as PrefManager
        }

    }

}