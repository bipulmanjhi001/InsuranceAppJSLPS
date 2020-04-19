package com.jslps.bimaseva

import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jslps.bimaseva.CustomProgressDialog
import com.jslps.bimaseva.listener.DialogListner


class DialogUtil {

    companion object {
        internal var currentDialog: Dialog? = null
        private var listener: DialogListner? = null
        private var dialogFragment: DialogFragment? = null

        fun setListner(list: DialogListner) {
            this.listener = list
        }

        internal var m_cProgressBar: CustomProgressDialog? = null
        fun hideKeyboard(view: View?, ctx: Context?) {
            if (view != null && ctx != null) {
                val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }


        fun displayProgress(pContext: Activity) {
            displayProgress(pContext, "Please wait..")
        }

        fun displayProgress(pContext: Activity?, message: String) {

            if (null == m_cProgressBar) {
                if (pContext == null) return
                if (pContext.isFinishing)
                    return
                m_cProgressBar = CustomProgressDialog(pContext)
                //  m_cProgressBar.getWindow().getAttributes().windowAnimations = R.style.ProgressAnimation;
                m_cProgressBar!!.setCancelable(false)
                m_cProgressBar!!.show()
            }
        }

//        fun showDialogWithOneButton(isCancel: Boolean, pContext: Activity, title: String?, msg: String?, okBtnText: String, okListener: View.OnClickListener) {
//            //        showIconDialogWithOneButton(pContext,title,msg,false,okBtnText,okListener);
//            showDialogWithOneButton(isCancel, pContext, title, msg, okBtnText, okListener)
//        }

//        fun showDialogWithOneButton(isCancel: Boolean, pContext: Activity?, title: String?, msg: String?, okBtnText: String, okListener: View.OnClickListener) {
//            //        showIconDialogWithOneButton(pContext,title,msg,false,okBtnText,okListener);
//            if (pContext == null) {
//                return
//            }
//            if (TextUtils.isEmpty(msg))
//                return
//
//            if (TextUtils.isEmpty(title))
//                return
//
//            try {
//                if (dialogFragment != null)
//                    return
//
//                val singleButtonDialog = SingleButtonDialog.newInstance()
//                singleButtonDialog.setTitle(title)
//                singleButtonDialog.setMessage(msg)
//                singleButtonDialog.setOkListner(okListener)
//                singleButtonDialog.setOkBtnText(okBtnText)
//                singleButtonDialog.setCancelable(isCancel)
//                singleButtonDialog.show(pContext.fragmentManager, singleButtonDialog.javaClass.name)
//                dialogFragment = singleButtonDialog
//            } catch (e: Exception) {
//            }
//
//        }

        fun showToast(context: Context, message: String?) {
            if (message.isNullOrEmpty())
                return
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun showToast(context: Context, message: Int) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun stopProgressDisplay() {
            if (null != m_cProgressBar && m_cProgressBar!!.isShowing) {
                try {
                    m_cProgressBar!!.dismiss()
                } catch (e: Exception) {

                }

            }
            m_cProgressBar = null
        }

        fun dismissDialog() {
            // TODO Auto-generated method stub
            //Log.d("DIALOGUtil", "dismissDialog: dialogFragment " + dialogFragment);
            if (currentDialog != null && currentDialog!!.isShowing) {
                currentDialog!!.dismiss()
            }
            if (dialogFragment != null && dialogFragment!!.isAdded) {
                dialogFragment!!.dismissAllowingStateLoss()
                dialogFragment = null
            }
            dialogFragment = null

        }

        fun isConnectionAvailable(context: Context?): Boolean {
            if (context == null) return false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}