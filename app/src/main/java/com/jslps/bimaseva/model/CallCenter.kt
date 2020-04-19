import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CallCenter(

	val Name: String,
	val NameofNominee: String,
	val Phno_ofNominee: String,
	val DistrictCode: String,
	val BlockCode: String,
	val ClusterCode: String,
	val VillageCode: String,
	val SHGCode: String,
	val BankCode: String,
	val BankBranch: String,
	val DateofIncidence: String,
	val TypeofInsurance: String,
	val OtherInsurance: String,
	val Phno_claim: String,
	val Name_Claim: String,
	val guid: String,
	val CreatedBy: String,
	val Gender: String,
	val Relation: String

): Parcelable
