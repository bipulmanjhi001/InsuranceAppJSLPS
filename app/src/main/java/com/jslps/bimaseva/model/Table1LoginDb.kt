import com.orm.SugarRecord


class Table1LoginDb : SugarRecord {
    var blockname: String? = null
        private set
    var blockcode: String? = null
        private set
    var districtcode: String? = null
        private set

    constructor() : super()
    constructor(blockname: String?, blockcode: String?, districtcode: String?) : super() {
        this.blockname = blockname
        this.blockcode = blockcode
        this.districtcode = districtcode
    }

    override fun toString(): String {
        return blockname.toString() // What to display in the Spinner list.
    }
}
