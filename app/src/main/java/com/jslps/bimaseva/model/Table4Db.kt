
import com.orm.SugarRecord

class Table4Db : SugarRecord {
    var clustercode: String? = null
        private set
    var villagecode: String? = null
        private set
    var SHGCode: String? = null
        private set
    var groupname: String? = null
        private set


    constructor() : super()
    constructor(
        clustercode: String?,
        villagecode: String?,
        SHGCode: String?,
        groupname: String?
    ) : super() {
        this.clustercode = clustercode
        this.villagecode = villagecode
        this.SHGCode = SHGCode
        this.groupname = groupname
    }
    override fun toString(): String {
        return groupname.toString() // What to display in the Spinner list.
    }
}