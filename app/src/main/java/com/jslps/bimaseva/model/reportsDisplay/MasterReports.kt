package com.jslps.bimaseva.model.reportsDisplay

import com.google.gson.annotations.SerializedName

class MasterReports (

    @SerializedName("DistrictName") val districtName : String,
    @SerializedName("BlockName") val blockName : String,
    @SerializedName("BankName") val bankName : String,
    @SerializedName("BranchName") val branchName : String,
    @SerializedName("Claim_Registered") val claimRegistered : String,
    @SerializedName("Claim_underprocess") val claimunderprocess : String,
    @SerializedName("Claim_setteled") val claimsetteled : String,
    @SerializedName("Claim_Reject") val claimReject : String,
    @SerializedName("TotalClaimAmount") val totalClaimAmount : String,
    @SerializedName("Claim_Document_Ready_but_not_submited") val Claim_Document_Ready_but_not_submited : String,
    @SerializedName("Claimcs") val Claimcs : String,
    @SerializedName("claimup") val claimup : String,
    @SerializedName("Reject") val Reject : String

)