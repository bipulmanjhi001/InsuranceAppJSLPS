package com.jslps.bimaseva.ui.scheme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener

class SchemeListAdapter(items: ArrayList<String>, activity: Context, insuranceName: String) :
    RecyclerView.Adapter<SchemeListAdapter.ViewHolder>() {

    var itemList: ArrayList<String>? = items
    var context: Context? = activity
    var insuranceName: String = insuranceName
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SchemeListAdapter.ViewHolder {
        val row = LayoutInflater.from(p0.context).inflate(R.layout.home_adapter_row, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text = itemList!![p1]
        when (p1) {
            0 -> p0.homeicon.setImageResource(R.drawable.registerd_icon)
            1 -> p0.homeicon.setImageResource(R.drawable.document_icon)
            2 -> p0.homeicon.setImageResource(R.drawable.process_icon)
            3 -> p0.homeicon.setImageResource(R.drawable.claim_setteld)
            4 -> p0.homeicon.setImageResource(R.drawable.rejected_icon)
            5 -> p0.homeicon.setImageResource(R.drawable.document_false)
            else -> p0.homeicon.setImageResource(R.drawable.reports_icon)
        }
        p0.view.setOnClickListener { mListner?.onListItemSelected(p1, insuranceName) }

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

    fun updateList(cardList: ArrayList<String>) {
        this.itemList = cardList
    }

}