package com.jslps.bimaseva.ui.documentFale

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.documentFale.adapter.DocumentFalseListAdapter
import com.jslps.bimaseva.ui.documentNotReady.DocumentFalseView


class DocumentFalseListFragment : BaseFragment(), DocumentFalseView,
    OnFragmentListItemSelectListener {
    override fun showMessage(message: Any?) {
        val toast = Toast.makeText(context, message as String, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun loadData(cardInitResponse: ArrayList<Master>?) {
        if (cardInitResponse != null) {
            recycleview?.visibility = View.VISIBLE
            if (homeRecyclerviewAdapter == null)
                homeRecyclerviewAdapter =
                    DocumentFalseListAdapter(cardInitResponse, activity as Activity, "")
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

    var homeRecyclerviewAdapter: DocumentFalseListAdapter? = null

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

    private var rootView: View? = null
    private var recycleview: RecyclerView? = null
    var presenter: DocumentFalsePresenter? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(
            Constant.setTitle,
            HeaderData(false, "Document False List")
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recycleview = rootView?.findViewById(R.id.recycleview)
        presenter = DocumentFalsePresenter(this, activity as Activity)
        recycleview?.layoutManager = Constant.getVerticalLayout(activity!!)
        presenter?.loadData(insuranceName)
        return rootView!!
    }

    companion object {
        var insuranceName: List<Master>? = null
        fun getInstance(insuranceNamee: List<Master>): DocumentFalseListFragment {
            insuranceName = insuranceNamee
            return DocumentFalseListFragment()
        }
    }
}

