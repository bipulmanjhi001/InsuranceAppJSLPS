package com.jslps.bimaseva.activity

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jslps.bimaseva.R
import com.jslps.bimaseva.model.reportsDisplay.MasterReports
import java.util.*

class EntryReportsRecyclerviewAdapter(
    private val context: Activity,
    var benifisheryDataModelDbArrayList: ArrayList<MasterReports>,
    var name: String?) : RecyclerView.Adapter<EntryReportsRecyclerviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ViewHolder {
        val row =
            LayoutInflater.from(context).inflate(R.layout.reports_entry_row_new, parent, false)
        return ViewHolder(row)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int) {
        when {
            name?.equals("Insurance2")!! -> {
                holder.nameofblock.visibility = View.GONE
                holder.nameofbank.visibility = View.GONE
                holder.nameofdistrict.text = benifisheryDataModelDbArrayList.get(position).districtName
                holder.nameofbranch.visibility = View.GONE
                holder.cr.text = benifisheryDataModelDbArrayList.get(position).claimRegistered
                holder.claimnu.text =
                    benifisheryDataModelDbArrayList.get(position).Claim_Document_Ready_but_not_submited
                holder.cliamsetted.text = benifisheryDataModelDbArrayList.get(position).claimsetteled
                holder.claimrejected.text = benifisheryDataModelDbArrayList.get(position).claimReject
                holder.cuprocess.text = benifisheryDataModelDbArrayList.get(position).claimunderprocess
            }
            name?.equals("Insurance1")!! -> {
                holder.nameofblock.visibility = View.VISIBLE
                holder.nameofbank.visibility = View.VISIBLE
                holder.nameofblock.text = benifisheryDataModelDbArrayList.get(position).blockName
                holder.nameofbank.text = benifisheryDataModelDbArrayList.get(position).bankName
                holder.nameofdistrict.text = benifisheryDataModelDbArrayList.get(position).districtName
                holder.nameofbranch.text = benifisheryDataModelDbArrayList.get(position).branchName
                holder.nameofbranch.visibility = View.VISIBLE
                holder.cr.text = benifisheryDataModelDbArrayList.get(position).claimRegistered
                holder.claimnu.text =
                    benifisheryDataModelDbArrayList.get(position).Claim_Document_Ready_but_not_submited
                holder.cliamsetted.text = benifisheryDataModelDbArrayList.get(position).claimsetteled
                holder.claimrejected.text = benifisheryDataModelDbArrayList.get(position).claimReject
                holder.cuprocess.text = benifisheryDataModelDbArrayList.get(position).claimunderprocess
            }
            else -> {
                holder.nameofblock.visibility = View.GONE
                holder.nameofbank.visibility = View.GONE
                holder.nameofbranch.visibility = View.GONE
                holder.nameofdistrict.text = benifisheryDataModelDbArrayList.get(position).bankName
                holder.cr.text = benifisheryDataModelDbArrayList.get(position).claimRegistered
                holder.claimnu.text =
                    benifisheryDataModelDbArrayList.get(position).Claim_Document_Ready_but_not_submited
                holder.cliamsetted.text = benifisheryDataModelDbArrayList.get(position).claimsetteled
                holder.claimrejected.text = benifisheryDataModelDbArrayList.get(position).claimReject
                holder.cuprocess.text = benifisheryDataModelDbArrayList.get(position).claimunderprocess
            }
        }


    }

    override fun getItemCount(): Int {
        return benifisheryDataModelDbArrayList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var nameofdistrict: TextView
        var claimnu: TextView
        var cr: TextView
        var cliamsetted: TextView
        var claimrejected: TextView
        var totalclaim: TextView
        var cuprocess: TextView
        var nameofbranch: TextView
        var nameofbank: TextView
        var nameofblock: TextView

        init {
            nameofdistrict = itemView.findViewById(R.id.nameofdistrict)
            cr = itemView.findViewById(R.id.cr)
            claimnu = itemView.findViewById(R.id.claimnu)
            cuprocess = itemView.findViewById(R.id.cuprocess)
            cliamsetted = itemView.findViewById(R.id.cliamsetted)
            claimrejected = itemView.findViewById(R.id.claimrejected)
            totalclaim = itemView.findViewById(R.id.totalclaim)
            nameofbranch = itemView.findViewById(R.id.nameofbranch)
            nameofbank = itemView.findViewById(R.id.nameofbank)
            nameofblock = itemView.findViewById(R.id.nameofblock)
        }
    }

}