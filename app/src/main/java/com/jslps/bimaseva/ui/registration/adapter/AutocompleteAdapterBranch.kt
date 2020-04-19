package com.jslps.bimaseva.ui.registration.adapter

import Table2Db
import Table5Db
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.jslps.bimaseva.R
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass

 class AutocompleteAdapterBranch(
     context: Context, @LayoutRes private val layoutResource: Int,
     private var listItemsTxt: List<Table5Db>,
     s: String
 ):
    ArrayAdapter<Table5Db>(context, layoutResource, listItemsTxt),
    Filterable {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mPois: List<Table5Db> = listItemsTxt
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
                val districtMasterClass = mPois as ArrayList<Table5Db>
                vh.label.text = districtMasterClass.get(position).branchname

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
                mPois = filterResults.values as List<Table5Db>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    listItemsTxt
                else
                    listItemsTxt.filter {
                                it.branchname?.toLowerCase()!!.contains(queryString)

                    }

                return filterResults
            }

        }
    }
    private class ItemRowHolder(row: View?) {

        val label: TextView

        init {
            this.label = row?.findViewById(R.id.text) as TextView
        }
    }
     override fun getItem(position: Int): Table5Db? {
         return if (mPois == null) null else mPois.get(position)

     }

     override fun getItemId(p0: Int): Long {
         // Or just return p0
         return p0.toLong()
     }
}