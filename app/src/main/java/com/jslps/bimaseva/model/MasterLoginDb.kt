
import android.R.attr.name
import com.orm.SugarRecord


open class MasterLoginDb : SugarRecord{
	 var districtcode: String? = null
		 private set
	 var districtname: String? = null
		 private set
	 var districtnameh: String? = null
		 private set
	 /*var blockname: String? = null
		 private set
	 var blockcode: String? = null
		 private set
	 var clustername: String? = null
		 private set
	 var ClusterCode: String? = null
		 private set
	 var villagename: String? = null
		 private set
	 var villageCode: String? = null
		 private set*/



    constructor() : super()
    constructor(districtcode: String?, districtname: String?, districtnameh: String?) : super() {
        this.districtcode = districtcode
        this.districtname = districtname
        this.districtnameh = districtnameh
    }

    override fun toString(): String {
        return districtname.toString() // What to display in the Spinner list.
    }
}
