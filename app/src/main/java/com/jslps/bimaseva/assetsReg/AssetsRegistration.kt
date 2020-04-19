package com.jslps.bimaseva.assetsReg

import CallCenter
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.ClaimRegistrationActivityOtpScreen
import com.jslps.bimaseva.activity.PoiAdapte
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.blockModel.BlockModelClass
import com.jslps.bimaseva.network.DistrictBlockClusterAndOtherGetList
import com.jslps.bimaseva.network.InsuranceCreateOTP
import kotlinx.android.synthetic.main.activity_assets_registration.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class AssetsRegistration : AppCompatActivity() {

    var animals_died: AutoCompleteTextView? = null
    var name: TextInputEditText? = null
    var Contact_Number: TextInputEditText? = null
    var nameofcaller: TextInputEditText? = null
    var mobileofcaller: TextInputEditText? = null
    var sppiner_district: AutoCompleteTextView? = null
    var spinnerPanchyt: AutoCompleteTextView? = null
    var spinnerBlock: AutoCompleteTextView? = null
    var spinnerVillage: AutoCompleteTextView? = null
    var spinnertype: Spinner? = null
    var spinnerShg: AutoCompleteTextView? = null
    var datePicker: TextView? = null
    var clustercode: String? = null
    var buttonSave: Button? = null
    var distirctCode: String? = null
    var blockCode: String? = null
    var shgCode: String? = null
    val list = arrayListOf<Int>()
    var radioGroup: RadioGroup? = null
    var villageCode: String? = null
    var bankCode: String? = null
    var branchCode: String? = null
    var panchyatModel: BlockMasterClass? = null
    var villageModel: BlockMasterClass? = null
    var shgModel: BlockMasterClass? = null
    var selectedtype: String? = null
    var blockModel: BlockMasterClass? = null
    var schemeID: String = "1";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assets_registration)
        setId()
        val intent = intent
        schemeID = intent.getStringExtra("schemeID")
        buttonSave = findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener {
            if (name?.text.toString().isEmpty()) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name")
                    .sneakError()
            } else if (animals_died?.text.toString().isEmpty()) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter animals died")
                    .sneakError()
            } else if (Contact_Number?.text.toString().isEmpty()) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter contact no of Beneficiary")
                    .sneakError()
            } else if (TextUtils.isEmpty(distirctCode)) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please select district")
                    .sneakError()
            } else if (TextUtils.isEmpty(blockCode)) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please select block")
                    .sneakError()
            } else if (TextUtils.isEmpty(clustercode)) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please select panchayat")
                    .sneakError()
            } else if (TextUtils.isEmpty(villageCode)) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please select village")
                    .sneakError()
            } else if (datePicker?.text.toString().isEmpty()) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter date")
                    .sneakError()
            } else if (nameofcaller?.text.toString().isEmpty()) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of caller")
                    .sneakError()
            } else if (mobileofcaller?.text.toString().isEmpty()) {
                Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of caller")
                    .sneakError()
            } else {
                //please configure this code
                val id = UUID.randomUUID().toString()
                val s = TextUtils.join(", ", list)
                val callCenter2 = CallCenter(
                    name?.text.toString(),
                    animals_died?.text.toString(),
                    Contact_Number?.text.toString(),
                    distirctCode!!,
                    blockCode!!,
                    clustercode!!,
                    villageCode.toString(),
                    shgCode!!,
                    selectedtype.toString(),
                    branchCode.toString(),
                    datePicker?.text.toString(),
                    schemeID, "",
                    mobileofcaller?.text.toString(),
                    nameofcaller?.text.toString(),
                    id,
                    "Admin",
                    "0",
                    "0"
                )
                val data = "{" + "\"CallCenter\"" + " : [" + Gson().toJson(callCenter2) + "] } "

                if (DialogUtil.isConnectionAvailable(this@AssetsRegistration)) {
                    DialogUtil.displayProgress(this@AssetsRegistration)
                    val gson = GsonBuilder().setLenient().create()
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val builder = OkHttpClient.Builder()
                    //comment in live build and uncomment in uat
                    builder.interceptors().add(interceptor)
                    builder.connectTimeout(250, TimeUnit.SECONDS)
                    builder.readTimeout(250, TimeUnit.SECONDS)
                    val client = builder.build()
                    val retrofit =
                        Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA).addConverterFactory(
                            ScalarsConverterFactory.create()
                        ).client(client).build()
                    val apiServices = retrofit.create(InsuranceCreateOTP::class.java)
                    val createInsurance = apiServices.createInsurance(mobileofcaller?.text.toString())
                    createInsurance.enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>
                        ) {
                            DialogUtil.stopProgressDisplay()
                            val fullResponse = response.body()
                            val XmlString =
                                fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                            val result = XmlString?.replace(("</string>").toRegex(), "")
                            val pref = getSharedPreferences("MyPrefInsuranceOTP", 0) // 0 - for private mode
                            val editor = pref.edit()
                            editor.putString("otp", result)
                            editor.apply()

                            Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                                .setTitle("OTP sent successfully ")
                                .sneakSuccess()
                            val intent = Intent(
                                this@AssetsRegistration,
                                ClaimRegistrationActivityOtpScreen::class.java
                            )
                            Handler().postDelayed(object:Runnable {
                                public override fun run() {
                                    intent.putExtra("data", callCenter2)
                                    startActivity(intent)
                                }
                            }, 2000)
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            DialogUtil.stopProgressDisplay()
                            Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                                .setTitle("Server Error Please Try Again")
                                .sneakError()
                        }
                    })
                } else {
                    Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                        .setTitle(Constant.NO_INTERNET)
                        .sneakError()
                }
            }
        }
        sppiner_district?.isEnabled = false
        spinnerPanchyt?.isEnabled = false
        spinnerVillage?.isEnabled = false
        spinnerBlock?.isEnabled = false
        spinnerShg?.isEnabled = false
        getDistrict()
        sppiner_district?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            distirctCode = selectedPoi?.districtCode
            sppiner_district?.setText(selectedPoi?.districtName)
            spinnerBlock?.clearFocus()
            spinnerBlock?.setText("")
            spinnerPanchyt?.clearFocus()
            spinnerPanchyt?.setText("")
            spinnerVillage?.clearFocus()
            spinnerVillage?.setText("")
            getBlockData(distirctCode.toString())
        }
        spinnerBlock?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.getItemAtPosition(position) as BlockMasterClass?
            blockCode = selectedPoi?.blockCode
            spinnerBlock?.setText(selectedPoi?.blockName)
            spinnerPanchyt?.clearFocus()
            spinnerPanchyt?.setText("")
            spinnerVillage?.clearFocus()
            spinnerVillage?.setText("")
            getClusterDataList(blockCode.toString())

        }
        spinnerVillage?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            villageCode = selectedPoi?.villageCode
            spinnerVillage?.setText(selectedPoi?.villageName)
            getSHGDataList(villageCode.toString())
        }
        spinnerPanchyt?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            clustercode = selectedPoi?.clusterCode
            spinnerPanchyt?.setText(selectedPoi?.clusterName)
            spinnerVillage?.clearFocus()
            spinnerVillage?.setText("")
            getVillageDataList(clustercode.toString())

        }
        spinnerShg?.setOnItemClickListener { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            shgCode = selectedPoi?.groupCode
            spinnerShg?.setText(selectedPoi?.group_Name)

        }
        spinnertype?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedtype = parent.getItemAtPosition(position).toString();
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        datePicker?.setOnClickListener {
            datePickerStrt()
        }

        radioGroup?.setOnCheckedChangeListener(
        RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            val radiovalue= radio.text;
            if(radiovalue.equals("SHG")){
                SHG.visibility = View.VISIBLE
            }
            else{
                SHG.visibility = View.GONE
            }
        })
    }

    private fun getDistrict() {
        if (DialogUtil.isConnectionAvailable(this)) {
            DialogUtil.displayProgress(this)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(250, TimeUnit.SECONDS)
            builder.readTimeout(250, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList("", "s", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    sppiner_district?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master,"district")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getBlockData(distictCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
            DialogUtil.displayProgress(this)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(250, TimeUnit.SECONDS)
            builder.readTimeout(250, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit =
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(distictCode, "D", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                )
                {
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    spinnerBlock?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "block")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getClusterDataList(blockCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
            DialogUtil.displayProgress(this)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            builder.interceptors().add(interceptor)
            builder.connectTimeout(250, TimeUnit.SECONDS)
            builder.readTimeout(250, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit =
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(blockCode, "B", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    spinnerPanchyt?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "cluster")
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getVillageDataList(clusterCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
            DialogUtil.displayProgress(this)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(250, TimeUnit.SECONDS)
            builder.readTimeout(250, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit =
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(clusterCode, "C", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    spinnerVillage?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "village")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getSHGDataList(villgaeCode: String) {
        if (DialogUtil.isConnectionAvailable(this)) {
            DialogUtil.displayProgress(this)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(250, TimeUnit.SECONDS)
            builder.readTimeout(250, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit =
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
                    .addConverterFactory(
                        ScalarsConverterFactory.create()
                    ).client(client).build()
            val apiServices = retrofit.create(DistrictBlockClusterAndOtherGetList::class.java)
            val changePhotoResponseModelCall =
                apiServices.fetchDistrictBlockClusterAndOtherGetList(villgaeCode, "V", "", "")
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, BlockModelClass::class.java)
                    spinnerShg?.isEnabled = true
                    updateSppinerDistrict(mStudentObject1.master, "shg")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@AssetsRegistration) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }
    private fun updateSppinerDistrict(master: ArrayList<BlockMasterClass>, value: String) {
        val adapter1 = PoiAdapte(this, R.layout.spiner_row, master, value)
        when (value) {
            "district" -> {
                sppiner_district?.setAdapter(adapter1)
                sppiner_district?.threshold = 1
            }
            "block" -> {
                spinnerBlock?.setAdapter(adapter1)
                spinnerBlock?.threshold = 1
            }
            "cluster" -> {
                spinnerPanchyt?.setAdapter(adapter1)
                spinnerPanchyt?.threshold = 1
            }
            "village" -> {
                spinnerVillage?.setAdapter(adapter1)
                spinnerVillage?.threshold = 1
            }
            "shg" -> {
                spinnerShg?.setAdapter(adapter1)
                spinnerShg?.threshold = 1
            }
        }
    }

    private fun setId() {

        animals_died = findViewById(R.id.animals_died)
        mobileofcaller = findViewById(R.id.mobileofbeneficiary)
        name = findViewById(R.id.name)
        Contact_Number = findViewById(R.id.Contact_Number)
        nameofcaller = findViewById(R.id.nameofcaller)
        buttonSave = findViewById(R.id.buttonSave)
        datePicker = findViewById(R.id.datepicker)
        sppiner_district = findViewById<AutoCompleteTextView>(R.id.sppiner_district)
        spinnerPanchyt = findViewById<AutoCompleteTextView>(R.id.spinner_panchayt)
        spinnerVillage = findViewById<AutoCompleteTextView>(R.id.spinner_village)
        spinnerBlock = findViewById<AutoCompleteTextView>(R.id.spinner_block)
        spinnerShg = findViewById<AutoCompleteTextView>(R.id.sppiner_shg)
        spinnertype=findViewById<Spinner>(R.id.type_of_asset)
        radioGroup = findViewById<RadioGroup>(R.id.radiotype)

    }

    private fun datePickerStrt(): Dialog {
        var c = Calendar.getInstance(Locale.ENGLISH)
        val ALyear = c.get(Calendar.YEAR)
        val ALmonthOfYear = c.get(Calendar.MONTH)
        val ALdayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val dateSelected =
                    (getProperFormat(dayOfMonth) + "-" + getProperFormat(monthOfYear + 1) + "-" + year)
                datePicker?.setText(dateSelected)
            }, ALyear, ALmonthOfYear, ALdayOfMonth
        )
        c.add(Calendar.YEAR, 5)
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
        return dpd
    }

    private fun getProperFormat(hhORmm: Int): String {
        var temp = hhORmm.toString()
        if (temp.length == 1) {
            temp = "0$temp"
        } else {
        }
        return temp
    }
}
