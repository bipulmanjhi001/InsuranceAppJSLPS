import com.google.gson.annotations.SerializedName


data class BaseClass (

	@SerializedName("Master") val master : List<MasterLogin>,
	@SerializedName("Table1") val table1 : List<Table1Login>,
	@SerializedName("Table2") val table2 : List<Table2>,
	@SerializedName("Table3") val table3 : List<Table3>,
	@SerializedName("Table4") val table4 : List<Table4>,
	@SerializedName("Table5") val table5 : List<Table5>,
	@SerializedName("Table6") val table6 : List<Table6>

)