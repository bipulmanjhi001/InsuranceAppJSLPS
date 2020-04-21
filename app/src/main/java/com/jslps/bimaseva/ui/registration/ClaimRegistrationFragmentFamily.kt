package com.jslps.bimaseva.ui.registration

import CallCenter
import MasterLoginDb
import Table1LoginDb
import Table2Db
import Table3Db
import Table4Db
import Table5Db
import Table6Db
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.activity.MainActivity
import com.jslps.bimaseva.adapter.CustomDropDownAdapter
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.model.blockModel.BlockMasterClass
import com.jslps.bimaseva.model.blockModel.BlockModelClass
import com.jslps.bimaseva.model.districtModel.DistirctModelClass
import com.jslps.bimaseva.model.districtModel.DistrictMasterClass
import com.jslps.bimaseva.network.DistrictBlockClusterAndOtherGetList
import com.jslps.bimaseva.network.InsuranceCreate
import com.jslps.bimaseva.ui.BaseFragment
import com.jslps.bimaseva.ui.registration.adapter.*
import com.orm.query.Condition
import com.orm.query.Select
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


class ClaimRegistrationFragmentFamily : BaseFragment() {

    var rootView:View?=null
    var nameOfnomminee: TextInputEditText? = null
    var name: TextInputEditText? = null
    var contactnoofnominee: TextInputEditText? = null
    var nameofcaller: TextInputEditText? = null
    var mobileofcaller: TextInputEditText? = null
    var sppiner_district: Spinner? = null
    var spinnerPanchyt: AutoCompleteTextView? = null
    var spinnerBlock: Spinner? = null
    var spinnerVillage: AutoCompleteTextView? = null
    var spinnerBank: AutoCompleteTextView? = null
    var spinnerBranch: AutoCompleteTextView? = null
    var spinnerShg: AutoCompleteTextView? = null
    var datePicker: TextView? = null
    var cal = Calendar.getInstance()
    var clustercode: String? = null
    var buttonSave: Button? = null
    var checkBox1: CheckBox? = null
    var checkBox2: CheckBox? = null
    var checkBox3: CheckBox? = null
    var checkBox4: CheckBox? = null
    var distirctCode: String? = null
    var blockCode: String? = null
    var shgCode: String? = null
    val list = arrayListOf<Int>()
    var radioButton: RadioButton? = null
    var radioGroup: RadioGroup? = null
    var genderId: Int? = null

    override fun onResume() {
        super.onResume()
        mListener?.onFragmentUpdate(Constant.setTitle,HeaderData(false,"Family Member Of SHG Claim Registration"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =
            inflater.inflate(R.layout.claim_registration_familymember_of_shg, container, false)
        setId()


        /*;*/
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
        val languages = resources.getStringArray(R.array.Relationship)
        val sppiner_relationship = rootView?.findViewById<Spinner>(R.id.sppiner_relationship)
        buttonSave = rootView?.findViewById(R.id.buttonSave)
        buttonSave?.text="Save Data"
        buttonSave?.setOnClickListener {
            if (name?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name")
                    .sneakError()
            } else if (nameOfnomminee?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of nominee")
                    .sneakError()
            } else if (contactnoofnominee?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of nominee")
                    .sneakError()
            } else if (TextUtils.isEmpty(distirctCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select district")
                    .sneakError()
            } else if (TextUtils.isEmpty(blockCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select block")
                    .sneakError()
            } else if (TextUtils.isEmpty(clustercode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select panchayat")
                    .sneakError()
            } else if (TextUtils.isEmpty(villageCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select village")
                    .sneakError()
            } else if (TextUtils.isEmpty(shgCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select SHG")
                    .sneakError()
            } else if (TextUtils.isEmpty(relationShipId)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select relationship ")
                    .sneakError()
            } else if (TextUtils.isEmpty(bankCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select bank")
                    .sneakError()
            } else if (TextUtils.isEmpty(branchCode)) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select branch")
                    .sneakError()
            } else if (datePicker?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter date")
                    .sneakError()
            } /*else if (list.size == 0) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select type of insurance")
                    .sneakError()
            }*/ else if (nameofcaller?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of caller")
                    .sneakError()
            } else if (mobileofcaller?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of caller")
                    .sneakError()
            } else {
                val selectedId = radioGroup?.getCheckedRadioButtonId();
                radioButton = rootView?.findViewById<RadioButton>(selectedId!!);
                genderId = if (radioButton?.text.toString().equals("Male")) {
                    1
                } else
                    2
                val id = UUID.randomUUID().toString()
                val s = TextUtils.join(", ", list)
                val prefs = activity?.getSharedPreferences(
                    "MyPrefInsurance", Context.MODE_PRIVATE
                );
                val createdBy = prefs?.getString("userName", "");
                val callCenter = CallCenter(
                    name?.text.toString(),
                    nameOfnomminee?.text.toString(),
                    contactnoofnominee?.text.toString(),
                    distirctCode!!,
                    blockCode!!,
                    clustercode!!,
                    villageCode.toString(),
                    shgCode!!,
                    bankCode.toString(),
                    branchCode.toString(),
                    datePicker?.text.toString(),
                    AppCache.getCache().schemeID.toString(), "",
                    mobileofcaller?.text.toString(),
                    nameofcaller?.text.toString(),
                    id,
                    createdBy.toString(),
                    genderId.toString(),
                    relationShipId.toString()
                )

                val data = "{" + "\"CallCenter\"" + " : [" + Gson().toJson(callCenter) + "] } "
                if (DialogUtil.isConnectionAvailable(activity!!)) {
                    DialogUtil.displayProgress(activity!!)
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
                        Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
                            ScalarsConverterFactory.create()
                        ).client(client).build()
                    val apiServices = retrofit.create(InsuranceCreate::class.java)
                    val createInsurance = apiServices.createInsurance(data)
                    createInsurance.enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>) {
                            DialogUtil.stopProgressDisplay()
                            val fullResponse = response.body()
                            val XmlString =
                                fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                            val result = XmlString?.replace(("</string>").toRegex(), "")
                            if (result.equals("\"1\"")) {
                                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                    .setTitle("Insurance Create Successfully ")
                                    .sneakSuccess()
                                val intent = Intent(activity, MainActivity::class.java)
                                Handler().postDelayed(object:Runnable {
                                    override fun run() {
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                    }
                                }, 2000)

                            } else {
                                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                    .setTitle("Please Try Again")
                                    .sneakError()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            DialogUtil.stopProgressDisplay()
                            val toast = Toast.makeText(activity, "Server Error Please Try Again", Toast.LENGTH_SHORT)
                            toast.show()
                            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                                .setTitle(t.toString())
                                .sneakError()
                        }
                    })
                } else {
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(Constant.NO_INTERNET)
                        .sneakError()
                }
            }
        }
        val adapter1 = ArrayAdapter(
            activity!!,
            R.layout.spiner_row, languages
        )
        sppiner_relationship?.adapter = adapter1

        sppiner_relationship?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> {
                        return
                    }
                    1 -> {
                        relationShipId = "1"
                    }
                    2 -> {
                        relationShipId = "2"
                    }
                    3 -> {
                        relationShipId = "3"
                    }
                    4 -> {
                        relationShipId = "4"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        val arraylistDistict: ArrayList<MasterLoginDb> =
            Select.from<MasterLoginDb>(
                MasterLoginDb::class.java
            ).list() as ArrayList<MasterLoginDb>

        val adapter = CustomDropDownAdapter_RegistrationInside(
            activity!!, "district", arraylistDistict
        )
        sppiner_district?.adapter = adapter

        sppiner_district?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val districtMasterClass = parent?.getItemAtPosition(position) as MasterLoginDb?
                distirctCode = districtMasterClass?.districtcode
                val arrayListBlock: ArrayList<Table1LoginDb> =
                    Select.from<Table1LoginDb>(
                        Table1LoginDb::class.java
                    ).where(
                        Condition.prop("districtcode").eq(distirctCode)
                    )
                        .list() as ArrayList<Table1LoginDb>

                val adapter = CustomDropDownAdapter_RegistrationInside(
                    activity!!, "block", arrayListBlock
                )
                spinnerBlock?.adapter = adapter
            }

        }
        spinnerBlock?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val shgCodeModel = parent?.getItemAtPosition(position) as Table1LoginDb
                blockCode = shgCodeModel.blockcode

            }

        }

        val arraylistPanchyat: ArrayList<Table2Db> =
            Select.from<Table2Db>(
                Table2Db::class.java
            ).list() as ArrayList<Table2Db>
        val adapterPanchyat = AutocompleteAdapterPanchayt(
            activity!!, R.layout.spiner_row_new, arraylistPanchyat, "cluster"
        )
        spinnerPanchyt?.setAdapter(adapterPanchyat)
        spinnerPanchyt?.threshold = 1
        val arraylistBank: ArrayList<Table6Db> =
            Select.from<Table6Db>(
                Table6Db::class.java
            ).list() as ArrayList<Table6Db>
        val adapterBankNew = AutocompleteAdapterBank(
            activity!!, R.layout.spiner_row_new, arraylistBank, "bank"
        )
        spinnerBank?.setAdapter(adapterBankNew)
        spinnerBank?.threshold = 1

        spinnerBank?.setOnItemClickListener() { parent, _, position, id ->
            spinnerBranch?.clearFocus()
            spinnerBranch?.setText("")
            val bankCodeModel = parent.adapter.getItem(position) as Table6Db
            bankCode = bankCodeModel.bankcode
            val arrayListBranch =
                Select.from<Table5Db>(
                    Table5Db::class.java).where(
                    Condition.prop("bankcode").eq(arraylistBank[position].bankcode))
                    .list() as ArrayList<Table5Db>
            val adapterBankNew = AutocompleteAdapterBranch(
                activity!!, R.layout.spiner_row_new,arrayListBranch,"branch"
            )
            spinnerBranch?.setAdapter(adapterBankNew)
            spinnerBranch?.threshold=1

        }
        spinnerBranch?.setOnItemClickListener() { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as Table5Db?
            branchCode = selectedPoi?.branchcode
            spinnerBranch?.setText(selectedPoi?.branchname)

        }

        spinnerVillage?.setOnItemClickListener() { parent, _, position, id ->
            val villageModel = parent?.getItemAtPosition(position) as Table3Db
            villageCode = villageModel.villagecode
            clustercode = villageModel.clustercode
            spinnerVillage?.setText(villageModel.villagename)
            val arrayListShg =
                Select.from<Table4Db>(
                    Table4Db::class.java).where(
                    Condition.prop("clustercode").eq(clustercode),
                    Condition.prop("villagecode").eq(villageCode))
                    .list() as ArrayList<Table4Db>
            val adapter = AutocompleteAdapterSHG(
                activity!!, R.layout.spiner_row_new,arrayListShg,"shg")
            spinnerShg?.setAdapter(adapter)
            spinnerShg?.threshold = 1

        }
        spinnerPanchyt?.setOnItemClickListener() { parent, _, position, id ->
            spinnerVillage?.clearFocus()
            spinnerVillage?.setText("")
            val panchaytModel = parent?.getItemAtPosition(position) as Table2Db?
            clustercode = panchaytModel?.clustercode
            spinnerPanchyt?.setText(panchaytModel?.clustername)
            val arrayListBlock = Select.from<Table3Db>(Table3Db::class.java).where(
                Condition.prop("clustercode").eq(clustercode))
                .list() as ArrayList<Table3Db>
            val adapter = AutocompleteAdapterVillage(
                activity!!, R.layout.spiner_row_new,arrayListBlock,"village")
            spinnerVillage?.setAdapter(adapter)
            spinnerVillage?.threshold = 1

        }
        spinnerShg?.setOnItemClickListener { parent, _, position, id ->
            val selectedPoi = parent.adapter.getItem(position) as Table4Db?
            shgCode = selectedPoi?.SHGCode
            spinnerShg?.setText(selectedPoi?.groupname)

        }
        return rootView
    }

    var villageCode: String? = null
    var relationShipId: String? = null
    var bankCode: String? = null
    var branchCode: String? = null
    private fun setId() {
        nameOfnomminee = rootView?.findViewById(R.id.nameOfnomminee)
        mobileofcaller = rootView?.findViewById(R.id.mobileofcaller)
        name = rootView?.findViewById(R.id.name)
        contactnoofnominee = rootView?.findViewById(R.id.contactnoofnominee)
        nameofcaller = rootView?.findViewById(R.id.nameofcaller)
        buttonSave = rootView?.findViewById(R.id.buttonSave)
        datePicker = rootView?.findViewById(R.id.datepicker)
        checkBox1 = rootView?.findViewById(R.id.checkBox1)
        checkBox2 = rootView?.findViewById(R.id.checkBox2)
        checkBox3 = rootView?.findViewById(R.id.checkBox3)
        checkBox4 = rootView?.findViewById(R.id.checkBox4)
        sppiner_district = rootView?.findViewById<Spinner>(R.id.sppiner_district)
        spinnerPanchyt = rootView?.findViewById<AutoCompleteTextView>(R.id.spinner_panchayt)
        spinnerVillage = rootView?.findViewById<AutoCompleteTextView>(R.id.spinner_village)
        spinnerBank = rootView?.findViewById<AutoCompleteTextView>(R.id.sppiner_bank)
        spinnerBlock = rootView?.findViewById<Spinner>(R.id.spinner_block)
        spinnerBranch = rootView?.findViewById<AutoCompleteTextView>(R.id.sppiner_branch)
        spinnerShg = rootView?.findViewById<AutoCompleteTextView>(R.id.sppiner_shg)
        radioGroup = rootView?.findViewById<RadioGroup>(R.id.radioSex)

    }

    private fun getDistrict() {
        if (DialogUtil.isConnectionAvailable(activity)) {
            DialogUtil.displayProgress(activity!!)
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
//                    DialogUtil.stopProgressDisplay()
                    val fullResponse = response.body()
                    val XmlString =
                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                    val result = XmlString?.replace(("</string>").toRegex(), "")
                    val mStudentObject1 =
                        gson.fromJson(result, DistirctModelClass::class.java)
                    updateSppinerDistrict(mStudentObject1.master)

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getBlockData(distictCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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

                    updateSppinerBlock(mStudentObject1.master, "block")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getClusterDataList(blockCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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
                    updateSppinerBlock(mStudentObject1.master, "cluster")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getVillageDataList(clusterCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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
                    updateSppinerBlock(mStudentObject1.master, "village")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getSHGDataList(villgaeCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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
                    updateSppinerBlock(mStudentObject1.master, "shg")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getBankList() {
        if (DialogUtil.isConnectionAvailable(activity)) {
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
                    updateSppinerBlock(mStudentObject1.master, "bank")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }

    private fun getBranchDataList(bankCode: String) {
        if (DialogUtil.isConnectionAvailable(activity!!)) {
            DialogUtil.displayProgress(activity!!)
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
                    updateSppinerBlock(mStudentObject1.master, "branch")

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    Sneaker.with(activity!!) // activity!!, Fragment or ViewGroup
                        .setTitle(t.toString())
                        .sneakError()
                }
            })
        } else {
            Sneaker.with(activity!!) // activity!!, Fragment or ViewGroup
                .setTitle(Constant.NO_INTERNET)
                .sneakError()
        }
    }
    private fun updateSppinerDistrict(master: ArrayList<DistrictMasterClass>) {
        master.add(0, DistrictMasterClass("","All District",""))
        val adapter = CustomDropDownAdapter(
            activity!!, "district", master
        )
        sppiner_district?.adapter = adapter
    }
    private fun updateSppinerBlock(master: ArrayList<BlockMasterClass>, dataType: String) {

      /*  when (dataType) {
            "block" -> {
                master.add(0, BlockMasterClass("","","All Block", "","",
                    "","","","","",
                    "","","","","","","",
                    "","","",""))
                val adapter = CustomDropDownAdapter(
                    activity!!, dataType, master
                )
                spinnerBlock?.adapter = adapter
            }

            "cluster" -> {
                master.add(0, BlockMasterClass("","","", "","",
                    "All Panchayat","","","","",
                    "","","","","","","",
                    "","","",""))

                val adapter = CustomDropDownAdapter(
                    activity!!, dataType, master
                )
                spinnerPanchyt?.adapter = adapter
            }

            "village" -> {
                master.add(0, BlockMasterClass("","","", "","",
                    "","","","All Village","",
                    "","","","","","","",
                    "","","",""))
              *//*  master.get(0).villageName = "All Village"*//*
                val adapter = CustomDropDownAdapter(
                    activity!!, dataType, master
                )
                spinnerVillage?.adapter = adapter
            }
            "shg" -> {
                master.add(0, BlockMasterClass("","","", "","",
                    "","","","",
                    "","","All SHG","","","","",
                    "","","","",""))
                val adapter = CustomDropDownAdapter(
                    activity!!, dataType, master
                )
                spinnerShg?.adapter = adapter
            }
            "bank" -> {
                master.add(0, BlockMasterClass("","","", "","",
                    "","","","",
                    "","","","","","","All Bank",
                    "","","","",""))
               *//* master.get(0).bankName = "All Bank"*//*
                val adapter = CustomDropDownAdapter(
                    activity!!, dataType, master
                )
                sppiner_bank?.adapter = adapter
            }
            "branch" -> {
                master.add(0, BlockMasterClass("","","", "","",
                    "","","","",
                    "","","","","","","",
                    "","","","All Branch",""))
              *//*  master.get(0).branchName = "All Branch"*//*
                val adapter = CustomDropDownAdapter(
                    activity!!, dataType, master
                )
                sppiner_branch?.adapter = adapter
            }
        }*/

    }


    private fun datePickerStrt(): Dialog {
        var c = Calendar.getInstance(Locale.ENGLISH)
        val ALyear = c.get(Calendar.YEAR)
        val ALmonthOfYear = c.get(Calendar.MONTH)
        val ALdayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(
            activity!!, R.style.datepicker,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val dateSelected =
                    (getProperFormat(dayOfMonth) + "-" + getProperFormat(monthOfYear + 1) + "-" + year)
                datePicker?.setText(dateSelected)
            }, ALyear, ALmonthOfYear, ALdayOfMonth
        )
        c.add(Calendar.YEAR, 2)

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
