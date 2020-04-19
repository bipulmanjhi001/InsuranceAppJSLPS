package com.jslps.bimaseva.model

data class UploadRegisterData(
    val call_id: String,
    val createdby: String,
    val guid: String,
    val imagebyte: String?,
    val createdon: String?,
    val Flag_Reg: String,
    val Image_UP: String?,
    val Flag_UP: String,
    val CreatedOn_Up: String?,
    val Image_CS: String?,
    val Flag_CS: String?,
    val Created_CS: String?,
    val Amount: String?,
    val Step:String,
    val RejectReason: String?,
    val Flag_NU :String?,
    val CreatedOn_NU :String
)