package com.jslps.bimaseva.ui.insuranceList

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.ui.BaseFragment

import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.ui.insuranceList.adapter.InsuranceListAdapter

class InsuranceListFragment : BaseFragment(), InsuranceView, OnFragmentListItemSelectListener {
    override fun showMessage(message: Any?) {
        val toast = Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun loadData(cardInitResponse: ArrayList<Master>?) {
        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter =
                    InsuranceListAdapter(cardInitResponse, activity as Activity, "")
            else
                homeRecyclerviewAdapter?.updateList(cardInitResponse)
            homeRecyclerviewAdapter?.setListner(presenter?.getListner())
            recycleview?.adapter = homeRecyclerviewAdapter
        } else {
            recycleview?.visibility = View.GONE
        }
    }

    override fun gotoScreen(fragmentID: Int, message: Any?) {
        if (message != null) {
            mListener?.onFragmentInteraction(fragmentID, message)
        }
    }

    var homeRecyclerviewAdapter: InsuranceListAdapter? = null

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun noInternet() {
    }

    override fun onListItemSelected(itemId: Int, data: Any) {
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    private var rootView: View? = null
    private var recycleview: RecyclerView? = null
    var presenter: InsurancePresenter? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(
            Constant.setTitle,
            HeaderData(false, AppCache.getCache().insurancetype.toString())
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)
        presenter = InsurancePresenter(this, activity as Activity)
        recycleview?.layoutManager = Constant.getVerticalLayout(activity!!)
        presenter?.resume(insuranceName)
        return rootView!!
    }

    companion object {
        var insuranceName: List<Master>? = null
        fun getInstance(insuranceNamee: List<Master>): InsuranceListFragment {
            insuranceName = insuranceNamee
            return InsuranceListFragment()
        }
    }
}

