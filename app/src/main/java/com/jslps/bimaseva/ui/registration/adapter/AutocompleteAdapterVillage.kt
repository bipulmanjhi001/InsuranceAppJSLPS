package com.jslps.bimaseva.ui.registration.adapter

import Table3Db
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.jslps.bimaseva.R

class AutocompleteAdapterVillage(
     context: Context, @LayoutRes private val layoutResource: Int,
     private var listItemsTxt: List<Table3Db>,
     s: String
 ):
    ArrayAdapter<Table3Db>(context, layoutResource, listItemsTxt),
    Filterable {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mPois: List<Table3Db> = listItemsTxt
     var dataType:String=s
    override fun getCount(): Int {
        return mPois.size
    }

     override fun getItemViewType(position: Int): Int {
         return super.getItemViewType(position)
     }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

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

                val districtMasterClass = mPois as ArrayList<Table3Db>
                vh.label.text = districtMasterClass.get(position).villagename
       /* val districtMasterClass = listItemsTxt as ArrayList<DistrictMasterClass>
        vh.label.text = districtMasterClass.get(position).districtName*/
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: Filter.FilterResults
            ) {
                mPois = filterResults.values as List<Table3Db>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    listItemsTxt
                else
                    listItemsTxt.filter {
                                it.villagename?.toLowerCase()!!.contains(queryString)

                    }

                return filterResults
            }

        }
    }
    private class ItemRowHolder(row: View?) {

        val label: TextView = row?.findViewById(R.id.text) as TextView

    }
     override fun getItem(position: Int): Table3Db? {
         return if (mPois == null) null else mPois.get(position)

     }

     override fun getItemId(p0: Int): Long {
         // Or just return p0
         return p0.toLong()
     }
}