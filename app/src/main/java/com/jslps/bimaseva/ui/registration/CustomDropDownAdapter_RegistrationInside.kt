package com.jslps.bimaseva.ui.registration

import MasterLoginDb
import Table1LoginDb
import Table2Db
import Table3Db
import Table4Db
import Table5Db
import Table6Db
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass

class CustomDropDownAdapter_RegistrationInside(val context: Context, val dataType: String, var listItemsTxt: List<*>) :
    BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.spiner_row_new, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programatically.


        when (dataType) {
            "district" -> {
                val districtMasterClass = listItemsTxt as ArrayList<MasterLoginDb>
                vh.label.text = districtMasterClass.get(position).districtname
            }
            "block" -> {
                val districtMasterClass = listItemsTxt as ArrayList<Table1LoginDb>
                vh.label.text = districtMasterClass.get(position).blockname
            }
            "cluster" -> {
                val districtMasterClass = listItemsTxt as ArrayList<Table2Db>
                vh.label.text = districtMasterClass.get(position).clustername
            }
            "village" -> {
                val districtMasterClass = listItemsTxt as ArrayList<Table3Db>
                vh.label.text = districtMasterClass.get(position).villagename
            }
            "shg" -> {
                val districtMasterClass = listItemsTxt as ArrayList<Table4Db>
                vh.label.text = districtMasterClass.get(position).groupname
            }
            "bank" -> {
                val districtMasterClass = listItemsTxt as ArrayList<Table6Db>
                vh.label.text = districtMasterClass.get(position).bankname
            }
            "branch" -> {
                val districtMasterClass = listItemsTxt as ArrayList<Table5Db>
                vh.label.text = districtMasterClass.get(position).branchname
            }
        }

        return view
    }

    override fun getItem(position: Int): Any? {
        return if (listItemsTxt == null) null else listItemsTxt.get(position)

    }

    override fun getItemId(position: Int): Long {
        return 0

    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView = row?.findViewById(R.id.text) as TextView

    }


}