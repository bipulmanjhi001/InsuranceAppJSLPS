package com.jslps.bimaseva.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentInteractionListener
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.twidpay.beta.cache.PrefManager

open class BaseFragment : Fragment() {
    internal var mListener: OnFragmentInteractionListener? = null
    protected var mActivity: AppCompatActivity? = null
    internal var screenName: String = ""
    internal var popupTypeHtml: String = "html"
    internal var popupTypeRegular: String = "regular"
    internal var TAG = "BaseFragment"
    internal var fragmentId: Int = 0
    internal var currentDialog: Dialog? = null

    fun setScreenName(screenName: String) {
        this.screenName = screenName
    }

    fun gotoNoInternet() {
        mListener?.onFragmentUpdate(Constant.UPDATE__NO_INTERNET, "")
    }

    fun setScreenName(id: Int, title: String) {
        this.screenName = title
        this.fragmentId = id
    }

    fun showProgressbar() {
        DialogUtil.displayProgress(mActivity!!)
    }

    fun hideProgressbar() {
        DialogUtil.stopProgressDisplay()
    }

    fun showToast(msg: String?) {
        if (!msg.isNullOrEmpty() && mActivity != null)
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }


    override fun onAttach(context: Context) {
        //    // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context as AppCompatActivity
        }

        if (context is OnFragmentInteractionListener) run { mListener = context }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        mActivity = null
    }

    fun getmActivity(): AppCompatActivity {
        return this.mActivity!!
    }

    fun getPref(activity: Activity): PrefManager {
        return PrefManager.getInstance(activity)
    }

    fun getAppCache(): AppCache {
        return AppCache.getCache()
    }



}