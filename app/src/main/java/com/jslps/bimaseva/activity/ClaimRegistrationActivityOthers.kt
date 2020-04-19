package com.jslps.bimaseva.activity

import CallCenter
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.adapter.CustomDropDownAdapter
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.blockModel.BlockModelClass
import com.jslps.bimaseva.model.districtModel.DistirctModelClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass
import com.jslps.bimaseva.network.DistrictBlockClusterAndOtherGetList
import com.jslps.bimaseva.network.InsuranceCreateOTP
import kotlinx.android.synthetic.main.claim_registration_shg.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class ClaimRegistrationActivityOthers : AppCompatActivity() {
    var nameOfnomminee: TextInputEditText? = null
    var name: TextInputEditText? = null
    var contactnoofnominee: TextInputEditText? = null
    var nameofcaller: TextInputEditText? = null
    var mobileofcaller: TextInputEditText? = null
    var sppiner_district: AutoCompleteTextView? = null
    var spinnerPanchyt: AutoCompleteTextView? = null
    var spinnerBlock: AutoCompleteTextView? = null
    var spinnerVillage: AutoCompleteTextView? = null
    var spinnerBank: AutoCompleteTextView? = null
    var spinnerBranch: AutoCompleteTextView? = null
    var datePicker: TextView? = null
    var cal = Calendar.getInstance()
    var clustercode: String? = null
    var viillagecode: String? = null
    var buttonSave: Button? = null
    var checkBox1: CheckBox? = null
    var checkBox2: CheckBox? = null
    var checkBox3: CheckBox? = null
    var checkBox4: CheckBox? = null
    var distirctCode: String? = null
    var blockCode: String? = null
    val list = arrayListOf<Int>()
    var villageCode: String? = null
    var bankCode: String? = null
    var branchCode: String? = null
    var panchyatModel: BlockMasterClass? = null
    var villageModel: BlockMasterClass? = null
    var blockModel: BlockMasterClass? = null
    var autotextView: AutoCompleteTextView? = null
    internal var preferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.claim_registration_others_new)
        setId()

        val intent = intent
        val schemeID = intent.getStringExtra("schemeID")
        supportActionBar?.title = "Other Claim Registration";
        buttonSave = findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener {
            val intent = Intent(this, ClaimRegistrationActivityOtpScreen::class.java)
            startActivity(intent)
        }
        checkBox1?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(1)
            else
                list.remove(1)
        }
        checkBox2?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(2)
            else
                list.remove(2)
        }
        checkBox3?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(3)
            else
                list.remove(3)
        }
        checkBox4?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                list.add(4)
            else
                list.remove(4)
        }
        buttonSave = findViewById(R.id.buttonSave)
        buttonSave?.setOnClickListener {
            if (name?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name")
                    .sneakError()
            } else if (nameOfnomminee?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of nominee")
                    .sneakError()
            } else if (contactnoofnominee?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of nominee")
                    .sneakError()
            } else if (TextUtils.isEmpty(distirctCode)) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please select district")
                    .sneakError()
            } else if (TextUtils.isEmpty(blockCode)) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please select block")
                    .sneakError()
            } else if (TextUtils.isEmpty(clustercode)) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please select panchayat")
                    .sneakError()
            } else if (TextUtils.isEmpty(villageCode)) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please select village")
                    .sneakError()
            } else if (TextUtils.isEmpty(bankCode)) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please select bank")
                    .sneakError()
            } else if (TextUtils.isEmpty(branchCode)) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please select branch")
                    .sneakError()
            } else if (datePicker?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter date")
                    .sneakError()
            } /*else if (list.size == 0) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please select type of insurance")
                    .sneakError()
            }*/ else if (nameofcaller?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of caller")
                    .sneakError()
            } else if (mobileofcaller?.text.toString().isEmpty()) {
                Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of caller")
                    .sneakError()
            } else {
                val id = UUID.randomUUID().toString()
                val s = TextUtils.join(", ", list)
                val callCenter = CallCenter(
                    name?.text.toString(),
                    nameOfnomminee?.text.toString(),
                    contactnoofnominee?.text.toString(),
                    distirctCode!!,
                    blockCode!!,
                    clustercode!!,
                    villageCode.toString(),
                    "",
                    bankCode.toString(),
                    branchCode.toString(),
                    datePicker?.text.toString(),
                    schemeID, "",
                    mobileofcaller?.text.toString(),
                    nameofcaller?.text.toString(),
                    id,
                    "Admin",
                    "", ""
                )
                if (DialogUtil.isConnectionAvailable(this@ClaimRegistrationActivityOthers)) {
                    DialogUtil.displayProgress(this@ClaimRegistrationActivityOthers)
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
                    val createInsurance =
                        apiServices.createInsurance(mobileofcaller?.text.toString())
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
                            val pref = getSharedPreferences(
                                "MyPrefInsuranceOTP",
                                0
                            ) // 0 - for private mode
                            val editor = pref.edit()
                            editor.putString("otp", result)
                            editor.apply()

                            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                                .setTitle("OTP sent successfully ")
                                .sneakSuccess()
                            val intent = Intent(
                                this@ClaimRegistrationActivityOthers,
                                ClaimRegistrationActivityOtpScreen::class.java
                            )
                            Handler().postDelayed(object : Runnable {
                                public override fun run() {
                                    intent.putExtra("data", callCenter)
                                    startActivity(intent)
                                }
                            }, 2000)


                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            DialogUtil.stopProgressDisplay()
                            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                                .setTitle("Server Error Please Try Again")
                                .sneakError()
                        }
                    })
                } else {
                    Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                        .setTitle(Constant.NO_INTERNET)
                        .sneakError()
                }
            }
        }
        sppiner_district?.isEnabled = false
        spinnerPanchyt?.isEnabled = false
        spinnerVillage?.isEnabled = false
        spinnerBlock?.isEnabled = false
        spinnerBank?.isEnabled = false
        spinnerBranch?.isEnabled = false


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
        spinnerBank?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            bankCode = selectedPoi?.bankCode
            spinnerBank?.setText(selectedPoi?.bankName)
            spinnerBranch?.clearFocus()
            spinnerBranch?.setText("")
            getBranchDataList(bankCode.toString())
        }
        spinnerBranch?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            branchCode = selectedPoi?.branchCode
            spinnerBranch?.setText(selectedPoi?.branchName)

        }
        spinnerVillage?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            villageCode = selectedPoi?.villageCode
            spinnerVillage?.setText(selectedPoi?.villageName)
        }
        spinnerPanchyt?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as BlockMasterClass?
            clustercode = selectedPoi?.clusterCode
            spinnerPanchyt?.setText(selectedPoi?.clusterName)
            spinnerVillage?.clearFocus()
            spinnerVillage?.setText("")
            getVillageDataList(clustercode.toString())

        }
        getBankList()
        datePicker?.setOnClickListener {
            datePickerStrt()
        }
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
            val retrofit =
                Retrofit.Builder().baseUrl(Constant.API_BASE_URL_JICA)
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
                    updateSppinerDistrict(mStudentObject1.master, "district")

                    /* val adapter = ArrayAdapter(this@ClaimRegistrationActivityOthers,
                         android.R.layout.simple_list_item_1, mStudentObject1.master)*/


                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getBankList() {
        if (DialogUtil.isConnectionAvailable(this)) {
//            DialogUtil.displayProgress(this)
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
                apiServices.fetchDistrictBlockClusterAndOtherGetList("", "BNK_NAME", "", "")
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
                    spinnerBank?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "bank")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getBranchDataList(bankCode: String) {
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
                apiServices.fetchDistrictBlockClusterAndOtherGetList(bankCode, "BRN_NAME", "", "")
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
                    spinnerBranch?.isEnabled=true
                    updateSppinerDistrict(mStudentObject1.master, "branch")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
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
                ) {
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
                    Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
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
                    Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
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
                    Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(this@ClaimRegistrationActivityOthers) // Activity, Fragment or ViewGroup
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

            "bank" -> {
                spinnerBank?.setAdapter(adapter1)
                spinnerBank?.threshold = 1
            }
            "branch" -> {
                spinnerBranch?.setAdapter(adapter1)
                spinnerBranch?.threshold = 1
            }
        }

    }

    /* private fun updateSppinerBlock(master: ArrayList<BlockMasterClass>, dataType: String) {

         when (dataType) {
             "block" -> {
                 master.add(
                     0, BlockMasterClass(
                         "", "", "All Block", "", "",
                         "", "", "", "", "",
                         "", "", "", "", "", "", "",
                         "", "", "", ""
                     )
                 )
                 val adapter = CustomDropDownAdapter(
                     this, dataType, master
                 )
                 spinnerBlock?.adapter = adapter
             }

             "cluster" -> {
                 master.add(
                     0, BlockMasterClass(
                         "", "", "", "", "",
                         "All Panchayat", "", "", "", "",
                         "", "", "", "", "", "", "",
                         "", "", "", ""
                     )
                 )

                 val adapter = CustomDropDownAdapter(
                     this, dataType, master
                 )
                 spinnerPanchyt?.adapter = adapter
             }

             "village" -> {
                 master.add(
                     0, BlockMasterClass(
                         "", "", "", "", "",
                         "", "", "", "All Village", "",
                         "", "", "", "", "", "", "",
                         "", "", "", ""
                     )
                 )
                 *//*  master.get(0).villageName = "All Village"*//*
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                spinnerVillage?.adapter = adapter
            }

            "bank" -> {
                master.add(
                    0, BlockMasterClass(
                        "", "", "", "", "",
                        "", "", "", "",
                        "", "", "", "", "", "", "All Bank",
                        "", "", "", "", ""
                    )
                )
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                sppiner_bank?.adapter = adapter
            }
            "branch" -> {
                master.add(
                    0, BlockMasterClass(
                        "", "", "", "", "",
                        "", "", "", "",
                        "", "", "", "", "", "", "",
                        "", "", "", "All Branch", ""
                    )
                )
                *//*  master.get(0).branchName = "All Branch"*//*
                val adapter = CustomDropDownAdapter(
                    this, dataType, master
                )
                sppiner_branch?.adapter = adapter
            }
        }

    }*/

    private fun setId() {
        nameOfnomminee = findViewById(R.id.nameOfnomminee)
        mobileofcaller = findViewById(R.id.mobileofcaller)
        name = findViewById(R.id.name)
        contactnoofnominee = findViewById(R.id.contactnoofnominee)
        nameofcaller = findViewById(R.id.nameofcaller)
        buttonSave = findViewById(R.id.buttonSave)
        datePicker = findViewById(R.id.datepicker)
        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        checkBox4 = findViewById(R.id.checkBox4)
        sppiner_district = findViewById<AutoCompleteTextView>(R.id.sppiner_district)
        spinnerPanchyt = findViewById<AutoCompleteTextView>(R.id.spinner_panchayt)
        spinnerVillage = findViewById<AutoCompleteTextView>(R.id.spinner_village)
        spinnerBank = findViewById<AutoCompleteTextView>(R.id.sppiner_bank)
        spinnerBlock = findViewById<AutoCompleteTextView>(R.id.spinner_block)
        spinnerBranch = findViewById<AutoCompleteTextView>(R.id.sppiner_branch)

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