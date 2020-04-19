package com.jslps.bimaseva.adapter

import android.content.Context
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
import com.jslps.bimaseva.model.welcomePageReports.BaseClassReportsWelcomwPage
import com.jslps.bimaseva.model.welcomePageReports.Listdetails


class CountWelcomePageListAdapter(
    items: ArrayList<Listdetails>,
    activity: Context,
    insuranceName: String
) :
    RecyclerView.Adapter<CountWelcomePageListAdapter.ViewHolder>() {
    var itemList: ArrayList<Listdetails>? = items
    var context: Context? = activity
    private var selectedPosition = -1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val row =
            LayoutInflater.from(p0.context).inflate(R.layout.welcome_adapter_row, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val listdetails = itemList?.get(p1)

    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

//        var num_txt: TextView = mView.findViewById(R.id.num_txt)
        var title: TextView = mView.findViewById(R.id.title)
        internal var view: View = mView

    }
}