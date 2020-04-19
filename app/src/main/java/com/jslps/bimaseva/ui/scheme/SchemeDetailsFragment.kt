package com.jslps.bimaseva.ui.scheme

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.scheme.adapter.SchemeListAdapter

class SchemeDetailsFragment : BaseFragment(), SchemeDetailsView, OnFragmentListItemSelectListener {
    override fun showMessage(message: String) {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    var homeRecyclerviewAdapter: SchemeListAdapter? = null
    override fun gotoScreen(fragmentID: Int, message: Any?) {
        if (fragmentID == 107)
            mListener?.onFragmentInteraction(fragmentID, "")
        else
            mListener?.onFragmentInteraction(fragmentID, message as List<Master>)
    }

    override fun loadData(cardInitResponse: ArrayList<String>?) {

        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter =
                    SchemeListAdapter(cardInitResponse, activity as Activity, insuranceName!!)
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
        val toast = Toast.makeText(activity, Constant.NO_INTERNET, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onListItemSelected(itemId: Int, data: Any) {
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    private var rootView: View? = null
    private var recycleview: RecyclerView? = null
    private var entryForm: Button? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    var presenter: SchemeDetailsPresenter? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, insuranceName!!))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)

        presenter = SchemeDetailsPresenter(this, activity as Activity)
        linearLayoutManager = LinearLayoutManager(getActivity());
        recycleview?.setLayoutManager(linearLayoutManager);
        entryForm?.visibility = View.GONE
        presenter?.resume()
        return rootView!!
    }

    companion object {
        var insuranceName: String? = null
        fun getInstance(insuranceNamee: String): SchemeDetailsFragment {
            insuranceName = insuranceNamee
            return SchemeDetailsFragment()
        }
    }
}

