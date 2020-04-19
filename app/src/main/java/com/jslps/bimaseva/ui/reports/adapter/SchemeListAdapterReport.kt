package com.jslps.bimaseva.ui.reports.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.MasterX

class SchemeListAdapterReport(items: ArrayList<MasterX>, activity: Context, insuranceName: String) :
    RecyclerView.Adapter<SchemeListAdapterReport.ViewHolder>() {

    var itemList: ArrayList<MasterX>? = items
    var context: Context? = activity
    var insuranceName: String = insuranceName
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SchemeListAdapterReport.ViewHolder {
        val row = LayoutInflater.from(p0.context).inflate(R.layout.home_adapter_row, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text =
            itemList?.get(p1)?.Column2.toString() + itemList?.get(p1)?.Column1.toString() + ")"
        when (p1) {
            0 -> p0.view.setOnClickListener { mListner?.onListItemSelected(p1, "Registered") }
            1 -> p0.view.setOnClickListener { mListner?.onListItemSelected(p1, "Document ready but not received by the branch") }
            2 -> p0.view.setOnClickListener { mListner?.onListItemSelected(p1, "Under Process") }
            3 -> p0.view.setOnClickListener { mListner?.onListItemSelected(p1, "Claim Settled") }
            4 -> p0.view.setOnClickListener { mListner?.onListItemSelected(p1, "Rejected") }
        }
        when (p1) {
            0 -> p0.homeicon.setImageResource(R.drawable.registerd_icon)
            1 -> p0.homeicon.setImageResource(R.drawable.document_icon)
            2 -> p0.homeicon.setImageResource(R.drawable.process_icon)
            3 -> p0.homeicon.setImageResource(R.drawable.claim_setteld)
            4 -> p0.homeicon.setImageResource(R.drawable.rejected_icon)
        }

    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var homeicon: ImageView = mView.findViewById(R.id.homeicon)
        var title: TextView = mView.findViewById(R.id.title)
        internal var view: View = mView

    }

    var mListner: OnFragmentListItemSelectListener? = null

    fun setListner(listner: OnFragmentListItemSelectListener?) {
        this.mListner = listner
    }

    fun updateList(cardList: ArrayList<MasterX>) {
        this.itemList = cardList
    }

}