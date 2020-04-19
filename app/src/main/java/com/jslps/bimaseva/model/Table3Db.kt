import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord


class Table3Db : SugarRecord {
	var villagename: String? = null
		private set
	var villagecode: String? = null
		private set
	var clustercode: String? = null
		private set

	constructor() : super()
	constructor(villagename: String?, villagecode: String?, clustercode: String?) : super() {
		this.villagename = villagename
		this.villagecode = villagecode
		this.clustercode = clustercode
	}
	override fun toString(): String {
		return villagename.toString() // What to display in the Spinner list.
	}
}


