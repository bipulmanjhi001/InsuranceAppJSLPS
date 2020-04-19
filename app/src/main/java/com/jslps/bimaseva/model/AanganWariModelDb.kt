package com.jslps.bimaseva.model

import com.orm.SugarRecord

class AanganWariModelDb : SugarRecord {
    var anganwadicode: String? = null
        private set
    var anganwadiname: String? = null
        private set
    var contactno: String? = null
        private set
    var createdby: String? = null
        private set
    var createdon: String? = null
        private set
    var type: String? = null
        private set
    var vocode: String? = null
        private set
    var awid: String? = null
        private set

    constructor() {}
    constructor(
        anganwadicode: String?,
        anganwadiname: String?,
        contactno: String?,
        createdby: String?,
        createdon: String?,
        type: String?,
        vocode: String?,
        awid: String?
    ) {
        this.anganwadicode = anganwadicode
        this.anganwadiname = anganwadiname
        this.contactno = contactno
        this.createdby = createdby
        this.createdon = createdon
        this.type = type
        this.vocode = vocode
        this.awid = awid
    }

}