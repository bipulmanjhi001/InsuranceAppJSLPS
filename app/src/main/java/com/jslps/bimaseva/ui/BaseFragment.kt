package com.jslps.bimaseva.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jslps.bimaseva.listener.OnFragmentInteractionListener


abstract class BaseFragment : Fragment() {

    internal var mListener: OnFragmentInteractionListener? = null
    private var mActivity: AppCompatActivity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //      // QLog.m11v("Class:" + getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        //  // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onCreate(savedInstanceState)
    }

    override fun onInflate(activity: Activity, attrs: AttributeSet, savedInstanceState: Bundle?) {
        //// // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onInflate(activity!!, attrs, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStart()
    }

    override fun onResume() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onResume()
    }

    override fun onPause() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onPause()
    }

    override fun onStop() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStop()
    }

    override fun onDestroyView() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroyView()
    }

    override fun onDestroy() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroy()
    }


    override fun onLowMemory() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onLowMemory()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener
        } else {
            //if(com.olive.upi.transport.TransportConstants.appRelease) Log.d("", "onAttach: mListener is not OnFragmentInteractionListener "+mListener);
            //            throw new RuntimeException(context.toString()
            //                    + " must implement OnFragmentInteractionListener");
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        mActivity = null
    }


    //    public void setUpToolBar(String title) {
    //        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    //        TextView screenTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
    //        screenTitle.setText(title);
    //    }


    //    public void setmScreenTitle(String title){
    //        this.title = title;
    //    }

    fun getmActivity(): AppCompatActivity? {
        return mActivity
    }

    companion object {

        fun isValidEmail(email: CharSequence): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }


}
