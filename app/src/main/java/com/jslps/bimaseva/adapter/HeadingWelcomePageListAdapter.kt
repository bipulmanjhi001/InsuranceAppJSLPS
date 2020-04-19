package com.jslps.bimaseva.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.model.welcomePageReports.BaseClassReportsWelcomwPage
import com.jslps.bimaseva.model.welcomePageReports.Listdetails


class HeadingWelcomePageListAdapter(
    items:  ArrayList<Listdetails>,
    activity: Context) :
    RecyclerView.Adapter<HeadingWelcomePageListAdapter.ViewHolder>() {
    var itemList: ArrayList<Listdetails>? = items
    var context: Context? = activity
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val row =
            LayoutInflater.from(p0.context).inflate(R.layout.welcome_adapter_row, p0, false)
        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val baseClassReportsWelcomwPage = itemList?.get(p1)

        p0.textrowheading.text = baseClassReportsWelcomwPage?.insurance
        p0.completed_count.text =
            baseClassReportsWelcomwPage?.completed.toString()
        p0.registerd_count.text =
            baseClassReportsWelcomwPage?.register.toString()
        p0.rejected_count.text =
            baseClassReportsWelcomwPage?.reject.toString()
        p0.underprocess_count.text =
            baseClassReportsWelcomwPage?.underProcess.toString()

    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var textrowheading: TextView = mView.findViewById(R.id.heading)
        var registerd_count: TextView = mView.findViewById(R.id.registerd_count)
        var underprocess_count: TextView = mView.findViewById(R.id.underprocess_count)
        var completed_count: TextView = mView.findViewById(R.id.completed_count)
        var rejected_count: TextView = mView.findViewById(R.id.rejected_count)
        internal var view: View = mView

    }
}