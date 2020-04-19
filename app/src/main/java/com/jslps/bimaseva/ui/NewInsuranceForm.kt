package com.jslps.bimaseva.ui

import CallCenter
import MasterLoginDb
import Table1LoginDb
import Table2Db
import Table3Db
import Table4Db
import Table5Db
import Table6Db
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.jslps.bimaseva.model.HeaderData
import com.jslps.bimaseva.network.InsuranceCreate
import com.orm.query.Condition
import com.orm.query.Select
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class NewInsuranceForm : BaseFragment() {
    var nameOfnomminee: TextInputEditText? = null
    var name: TextInputEditText? = null
    var contactnoofnominee: TextInputEditText? = null
    var nameofcaller: TextInputEditText? = null
    var mobileofcaller: TextInputEditText? = null
    var spinner: Spinner? = null
    var spinnerPanchyt: Spinner? = null
    var spinnerBlock: Spinner? = null
    var spinnerVillage: Spinner? = null
    var spinnerBank: Spinner? = null
    var spinnerBranch: Spinner? = null
    var spinnerShg: Spinner? = null
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
    var shgCode: String? = null
    val list = arrayListOf<Int>()
    var arrayListBlock: ArrayList<Table3Db>? = null
    var arrayListShg: ArrayList<Table4Db>? = null
    var radioButton: RadioButton? = null
    var radioGroup: RadioGroup? = null
    var genderId: Int? = null
    override fun onResume() {
        super.onResume()
        mListener!!.onFragmentUpdate(Constant.setTitle, HeaderData(false, "Claim Registration"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.claim_registration_shg, container, false)
        setId(rootView)

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
            } else if (datePicker?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter date")
                    .sneakError()
            } else if (list.size == 0) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please select type of insurance")
                    .sneakError()
            } else if (nameofcaller?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter name of caller")
                    .sneakError()
            } else if (mobileofcaller?.text.toString().isEmpty()) {
                Sneaker.with(activity!!) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter mobile no of caller")
                    .sneakError()
            } else {
                val selectedId = radioGroup?.getCheckedRadioButtonId();
                radioButton = rootView.findViewById<RadioButton>(selectedId!!);
                if (radioButton?.text.toString().equals("Male")) {
                    genderId = 1
                } else
                    genderId = 2
                val id = UUID.randomUUID().toString()
                blockCode = arrayListBlock?.get(0)?.villagecode
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
                    viillagecode!!,
                    shgCode!!,
                    bankCode!!,
                    branchCode!!,
                    datePicker?.text.toString(),
                    s, "",
                    mobileofcaller?.text.toString(),
                    nameofcaller?.text.toString(),
                    id,
                    createdBy!!,
                    genderId?.toString()!!,
                "")
                val data = "{" + "\"CallCenter\"" + " : [" + Gson().toJson(callCenter) + "] } "
                if (DialogUtil.isConnectionAvailable(activity!!)) {
                    DialogUtil.displayProgress(activity!!)
                    val gson = GsonBuilder().setLenient().create()
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val builder = OkHttpClient.Builder()
                    //comment in live build and uncomment in uat
                    builder.interceptors().add(interceptor)
                    builder.connectTimeout(120, TimeUnit.SECONDS)
                    builder.readTimeout(120, TimeUnit.SECONDS)
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
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)

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
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }
        datePicker?.setOnClickListener {
            DatePickerDialog(
                activity!!, R.style.datepicker,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        val arraylistDistict: ArrayList<MasterLoginDb> =
            Select.from<MasterLoginDb>(
                MasterLoginDb::class.java
            ).list() as ArrayList<MasterLoginDb>

        val adapterDistrict: ArrayAdapter<MasterLoginDb> = ArrayAdapter<MasterLoginDb>(
            activity!!,
            R.layout.spiner_row_new,
            arraylistDistict
        )
        spinner?.adapter = adapterDistrict
        val arraylistPanchyat: ArrayList<Table2Db> =
            Select.from<Table2Db>(
                Table2Db::class.java
            ).list() as ArrayList<Table2Db>

        val adapterPanchayt: ArrayAdapter<Table2Db> = ArrayAdapter<Table2Db>(
            activity!!,
            R.layout.spiner_row_new,
            arraylistPanchyat
        )
        spinnerPanchyt?.setAdapter(adapterPanchayt)
        spinnerPanchyt?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                clustercode = arraylistPanchyat[position].clustercode
               val arrayListBlock =
                    Select.from<Table3Db>(
                        Table3Db::class.java
                    ).where(
                        Condition.prop("clustercode").eq(clustercode)
                    )
                        .list() as ArrayList<Table3Db>
                val adapterBlock: ArrayAdapter<Table3Db> = ArrayAdapter<Table3Db>(
                    activity!!,
                    R.layout.spiner_row_new,
                    arrayListBlock!!
                )
                spinnerVillage?.adapter = adapterBlock
            }

        }
        spinnerVillage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viillagecode = arrayListBlock?.get(position)?.villagecode
                arrayListShg =
                    Select.from<Table4Db>(
                        Table4Db::class.java
                    ).where(
                        Condition.prop("clustercode").eq(clustercode)/*,
                        Condition.prop("villagecode").eq(arrayListBlock?.get(position)?.villagecode)*/
                    )
                        .list() as ArrayList<Table4Db>
                Select.from<Table3Db>(
                    Table3Db::class.java
                ).where(
                    Condition.prop("clustercode").eq(clustercode)/*,
                        Condition.prop("villagecode").eq(arrayListBlock?.get(position)?.villagecode)*/
                )
                    .list() as ArrayList<Table4Db>

                val adapterBlock: ArrayAdapter<Table4Db> = ArrayAdapter<Table4Db>(
                    activity!!,
                    R.layout.spiner_row_new,
                    arrayListShg!!
                )
                spinnerShg?.setAdapter(adapterBlock)
            }

        }
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                distirctCode = arraylistDistict.get(position).districtcode
                val arrayListBlock: ArrayList<Table1LoginDb> =
                    Select.from<Table1LoginDb>(
                        Table1LoginDb::class.java)
                        .where(
                            Condition.prop("districtcode").eq(arraylistDistict[position].districtcode)
                        )
                        .list() as ArrayList<Table1LoginDb>
                val adapterBlock: ArrayAdapter<Table1LoginDb> = ArrayAdapter<Table1LoginDb>(
                    activity!!,
                    R.layout.spiner_row_new,
                    arrayListBlock
                )
                spinnerBlock?.setAdapter(adapterBlock)
            }

        }

        val arraylistBank: ArrayList<Table6Db> =
            Select.from<Table6Db>(
                Table6Db::class.java
            ).list() as ArrayList<Table6Db>

        val adapterBank: ArrayAdapter<Table6Db> = ArrayAdapter<Table6Db>(
            activity!!,
            R.layout.spiner_row_new,
            arraylistBank
        )

        spinnerBank?.adapter = adapterBank
        spinnerBank?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bankCode = arraylistBank[position].bankcode
                arrayListBranch =
                    Select.from<Table5Db>(
                        Table5Db::class.java).where(
                        Condition.prop("bankcode").eq(arraylistBank[position].bankcode)
                    )
                        .list() as ArrayList<Table5Db>
                if (arrayListBranch != null && arrayListBranch!!.size > 0) {
                    val adapterBranch: ArrayAdapter<Table5Db> = ArrayAdapter<Table5Db>(
                        activity!!,
                        R.layout.spiner_row_new,
                        arrayListBranch!!
                    )
                    spinnerBranch?.setAdapter(adapterBranch)
                }

            }

        }
        spinnerBranch?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                branchCode = arrayListBranch?.get(position)?.branchcode
            }

        }
        spinnerShg?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                shgCode = arrayListShg?.get(position)?.SHGCode

            }

        }

        return rootView
    }

    var arrayListBranch: ArrayList<Table5Db>? = null
    var bankCode: String? = null
    var branchCode: String? = null
    private fun setId(rootView: View) {
        nameOfnomminee = rootView.findViewById(R.id.nameOfnomminee)
        mobileofcaller = rootView.findViewById(R.id.mobileofcaller)
        name = rootView.findViewById(R.id.name)
        contactnoofnominee = rootView.findViewById(R.id.contactnoofnominee)
        nameofcaller = rootView.findViewById(R.id.nameofcaller)
        buttonSave = rootView.findViewById(R.id.buttonSave)
        datePicker = rootView.findViewById(R.id.datepicker)
        checkBox1 = rootView.findViewById(R.id.checkBox1)
        checkBox2 = rootView.findViewById(R.id.checkBox2)
        checkBox3 = rootView.findViewById(R.id.checkBox3)
        checkBox4 = rootView.findViewById(R.id.checkBox4)
        spinner = rootView.findViewById<Spinner>(R.id.sppiner_district)
        spinnerPanchyt = rootView.findViewById<Spinner>(R.id.spinner_panchayt)
        spinnerVillage = rootView.findViewById<Spinner>(R.id.spinner_village)
        spinnerBank = rootView.findViewById<Spinner>(R.id.sppiner_bank)
        spinnerBlock = rootView.findViewById<Spinner>(R.id.spinner_block)
        spinnerBranch = rootView.findViewById<Spinner>(R.id.sppiner_branch)
        spinnerShg = rootView.findViewById<Spinner>(R.id.sppiner_shg)
        radioGroup = rootView.findViewById<RadioGroup>(R.id.radioSex)

    }

    companion object {
        fun getInstance(): NewInsuranceForm {
            return NewInsuranceForm()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datePicker!!.text = sdf.format(cal.getTime())
    }
}