package com.jslps.bimaseva.ui.reports.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.Master


class ReportListAdapter(items: ArrayList<Master>, activity: Context, insuranceName: String) :
    RecyclerView.Adapter<ReportListAdapter.ViewHolder>() {

    var itemList: ArrayList<Master>? = items
    var context: Context? = activity
    var insuranceNamee: String = insuranceName
    private var selectedPosition = -1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val row = LayoutInflater.from(p0.context).inflate(R.layout.row_report_list, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val masterModel = itemList?.get(p1)
        p0.nomineeName.text = masterModel?.name
        p0.contactNo.text = masterModel?.phno_ofNominee
        if (masterModel?.insuranceTypeFetch == "Registered") {
            p0.statusayout.visibility = View.VISIBLE
            p0.textStatus.text = "Document " + masterModel?.Status
            when (masterModel.Status) {
                "True" -> p0.textStatus.setTextColor(Color.parseColor("#098000"))
                "false" -> p0.textStatus.setTextColor(Color.parseColor("#F00"))
                else -> p0.textStatus.setTextColor(Color.parseColor("#2E2C8F"))
            }
        } else {
            p0.statusayout.visibility = View.GONE
        }
        p0.nameOfInsurance.text = AppCache.getCache().insurancetype


    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var nameOfInsurance: TextView = mView.findViewById(R.id.insuranceName)
        var nomineeName: TextView = mView.findViewById(R.id.nomineeInsurance)
        var contactNo: TextView = mView.findViewById(R.id.contactNo)
        var textStatus: TextView = mView.findViewById(R.id.textStatus)
        var statusayout: LinearLayout = mView.findViewById(R.id.statusayout)
        internal var view: View = mView

    }

    var mListner: OnFragmentListItemSelectListener? = null

    fun setListner(listner: OnFragmentListItemSelectListener?) {
        this.mListner = listner
    }

    fun updateList(cardList: ArrayList<Master>) {
        this.itemList = cardList
    }

}