package com.jslps.bimaseva.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.WelcomeActivity
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.home.adapter.CardListAdapter


class HomeFragment : BaseFragment(), HomeView, OnFragmentListItemSelectListener {
    private var homeRecyclerviewAdapter: CardListAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    override fun gotoScreen(fragmentID: Int, message: Any?) {
        if (fragmentID == Constant.INSURANCE_CREATE_INSIDE) {
            mListener?.onFragmentInteraction(Constant.INSURANCE_CREATE_INSIDE,"")
        } else
            mListener?.onFragmentInteraction(fragmentID, message as String)
    }

    override fun loadData(cardInitResponse: ArrayList<String>?) {

        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter =
                    CardListAdapter(cardInitResponse, activity as Activity)
            else
                homeRecyclerviewAdapter?.updateList(cardInitResponse)
            homeRecyclerviewAdapter?.setListner(presenter?.getListner())
            recycleview?.adapter = homeRecyclerviewAdapter
        } else {
            recycleview?.visibility = View.GONE
        }
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun noInternet() {
        val toast = Toast.makeText(context, Constant.NO_INTERNET, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onListItemSelected(itemId: Int, data: Any) {
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    private var entryForm: Button? = null
    private var rootView: View? = null
    private var logout: ImageView? = null
    private var recycleview: RecyclerView? = null
    var presenter: HomePresenter? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(true, "Dashboard"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home_new, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)

        logout = rootView?.findViewById(R.id.logout)
        presenter = HomePresenter(this, activity as Activity)
        linearLayoutManager = LinearLayoutManager(getActivity());
        recycleview?.setLayoutManager(linearLayoutManager);
        //add ItemDecoration
//        recycleview?.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
        entryForm?.visibility = View.GONE
        entryForm?.setOnClickListener {
            mListener?.onFragmentInteraction(Constant.ENTRY_FORM_INSURANCE, "")
        }
        logout?.setOnClickListener {
            logoutApp()
        }
        presenter?.resume()

        return rootView!!
    }

    private var preferences: SharedPreferences? = null
    fun logoutApp() {
        val alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
        alertDialogBuilder.setMessage(getString(R.string.logout_message))
        alertDialogBuilder.setPositiveButton("yes",
            DialogInterface.OnClickListener { arg0, arg1 ->
                val preferences: SharedPreferences =
                    activity?.getSharedPreferences("MyPrefInsurance", Context.MODE_PRIVATE)!!
                val editor = preferences.edit()
                editor.clear()
                editor.commit()
                val intent = Intent(activity, WelcomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            })
        alertDialogBuilder.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}

