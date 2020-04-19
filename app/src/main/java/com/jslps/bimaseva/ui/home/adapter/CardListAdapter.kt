package com.jslps.bimaseva.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener

class CardListAdapter(items: ArrayList<String>, activity: Context) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {

    var itemList: ArrayList<String>? = items
    var context: Context? = activity

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardListAdapter.ViewHolder {
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
            2 -> p0.homeicon.setImageResource(R.drawable.pmjayicon)
            3 -> p0.homeicon.setImageResource(R.drawable.pmsbyicon)
            4 -> p0.homeicon.setImageResource(R.drawable.imagedownload)
            else -> p0.homeicon.setImageResource(R.drawable.insurance)
        }
        p0.view.setOnClickListener { mListner?.onListItemSelected(p1, itemList!![p1]) }
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var title: TextView = mView.findViewById(R.id.title)
        var homeicon: ImageView = mView.findViewById(R.id.homeicon)
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