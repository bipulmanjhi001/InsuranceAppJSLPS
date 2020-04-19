package com.jslps.bimaseva.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.jslps.bimaseva.R
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass

 class PoiAdapte(
     context: Context, @LayoutRes private val layoutResource: Int,
     private var listItemsTxt: List<BlockMasterClass>,
     s: String
 ):
    ArrayAdapter<BlockMasterClass>(context, layoutResource, listItemsTxt),
    Filterable {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mPois: List<BlockMasterClass> = listItemsTxt
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
        when (dataType) {
            "district" -> {
                val districtMasterClass = mPois as ArrayList<BlockMasterClass>
                vh.label.text = districtMasterClass.get(position).districtName
            }
            "block" -> {
                val districtMasterClass = mPois as ArrayList<BlockMasterClass>
                vh.label.text = districtMasterClass.get(position).blockName
            }
            "cluster" -> {
                val districtMasterClass = mPois as ArrayList<BlockMasterClass>
                vh.label.text = districtMasterClass.get(position).clusterName
            }
            "village" -> {
                val districtMasterClass = mPois as ArrayList<BlockMasterClass>
                vh.label.text = districtMasterClass.get(position).villageName
            }
            "shg" -> {
                val districtMasterClass = mPois as ArrayList<BlockMasterClass>
                vh.label.text = districtMasterClass.get(position).group_Name
            }
            "bank" -> {
                val districtMasterClass = mPois as ArrayList<BlockMasterClass>
                vh.label.text = districtMasterClass.get(position).bankName
            }
            "branch" -> {
                val districtMasterClass = mPois as ArrayList<BlockMasterClass>
                vh.label.text = districtMasterClass.get(position).branchName
            }
        }
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
                mPois = filterResults.values as List<BlockMasterClass>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    listItemsTxt
                else
                    listItemsTxt.filter {
                        when (dataType) {
                            "district" -> {
                                it.districtName.toLowerCase().contains(queryString)
                            }
                            "block" -> {
                                it.blockName.toLowerCase().contains(queryString)
                            }
                            "cluster" -> {
                                it.clusterName.toLowerCase().contains(queryString)
                            }
                            "village" -> {
                                it.villageName.toLowerCase().contains(queryString)
                            }
                            "shg" -> {
                                it.group_Name.toLowerCase().contains(queryString)
                            }
                            "bank" -> {
                                it.bankName.toLowerCase().contains(queryString)
                            }
                            "branch" -> {
                                it.branchName.toLowerCase().contains(queryString)
                            }
                            else ->  it.districtName.toLowerCase().contains(queryString)
                        }

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
     override fun getItem(position: Int): BlockMasterClass? {
         return if (mPois == null) null else mPois.get(position)

     }

     override fun getItemId(p0: Int): Long {
         // Or just return p0
         return p0.toLong()
     }
}