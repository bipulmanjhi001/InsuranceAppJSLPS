package com.jslps.bimaseva.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.model.reportsDisplay.BaseClassReports
import com.jslps.bimaseva.model.reportsDisplay.MasterReports
import com.jslps.bimaseva.network.ReportsEntryService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class WelcomeActivityNewNext : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var bank: TextView? = null
    var branch: TextView? = null
    var block: TextView? = null
    var district: TextView? = null
    var frameLayout: FrameLayout? = null
    var sppiner_scheme: Spinner? = null
    var schemeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_new_next)
        bank = findViewById(R.id.bank)
        branch = findViewById(R.id.branch)
        district = findViewById(R.id.district)
        block = findViewById(R.id.block)
        frameLayout = findViewById(R.id.frameLayout)
        sppiner_scheme = findViewById(R.id.sppiner_scheme)
        val intent = intent
        val name = intent.getStringExtra("clicktype")// Activity, Fragment or ViewGroup
        when {
            name.equals("Insurance2") -> {
                branch?.visibility = View.GONE
                block?.visibility = View.GONE
                bank?.visibility = View.GONE
                district?.visibility = View.VISIBLE
                frameLayout?.visibility = View.VISIBLE
                supportActionBar?.title = "District Wise Report";
                /*if (name.equals("Insurance2")) {
                    if (DialogUtil.isConnectionAvailable(this@WelcomeActivityNewNext)) {
                        DialogUtil.displayProgress(this@WelcomeActivityNewNext)
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
                            Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                .addConverterFactory(
                                    ScalarsConverterFactory.create()
                                ).client(client).build()
                        val apiServices = retrofit.create(ReportsEntryService::class.java)
                        val changePhotoResponseModelCall =
                            apiServices.getReportsEntryService("Insurance2","")
                        changePhotoResponseModelCall.enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                DialogUtil.stopProgressDisplay()
                                val gson = Gson()
                                val fullResponse = response.body()
                                val XmlString =
                                    fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                val result = XmlString?.replace(("</string>").toRegex(), "")
                                val mStudentObject1 =
                                    gson.fromJson(result, BaseClassReports::class.java)
                                System.out.println("vvh" + gson.toJson(mStudentObject1))
                                val benifisheryRowRecyclerviewAdapter =
                                    EntryReportsRecyclerviewAdapter(
                                        this@WelcomeActivityNewNext,
                                        mStudentObject1.master as ArrayList<MasterReports>,
                                        name
                                    )
                                recyclerView?.setAdapter(benifisheryRowRecyclerviewAdapter)

                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                DialogUtil.stopProgressDisplay()
                                Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                    .setTitle("Server error,Please Try Again")
                                    .sneakError()
                            }
                        })
                    } else {

                        Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                            .setTitle(Constant.NO_INTERNET)
                            .sneakError()
                    }
                }*/
            }
            name.equals("Insurance1") -> {
                branch?.visibility = View.VISIBLE
                bank?.visibility = View.VISIBLE
                block?.visibility = View.VISIBLE
                district?.visibility = View.VISIBLE
                frameLayout?.visibility = View.VISIBLE
                supportActionBar?.title = "All Report";
                /* bank?.text = "Bank Name"
                 branch?.text = "Branch Name"*/
            }
            name.equals("Insurance3") -> {
                branch?.visibility = View.GONE
                bank?.visibility = View.VISIBLE
                block?.visibility = View.GONE
                district?.visibility = View.GONE
                frameLayout?.visibility = View.VISIBLE
                supportActionBar?.title = "Bank Wise Report";
                /*if (name.equals("Insurance3")) {
                    if (DialogUtil.isConnectionAvailable(this@WelcomeActivityNewNext)) {
                        DialogUtil.displayProgress(this@WelcomeActivityNewNext)
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
                            Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                .addConverterFactory(
                                    ScalarsConverterFactory.create()
                                ).client(client).build()
                        val apiServices = retrofit.create(ReportsEntryService::class.java)
                        val changePhotoResponseModelCall =
                            apiServices.getReportsEntryService("Insurance3","")
                        changePhotoResponseModelCall.enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                DialogUtil.stopProgressDisplay()
                                val gson = Gson()
                                val fullResponse = response.body()
                                val XmlString =
                                    fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                val result = XmlString?.replace(("</string>").toRegex(), "")
                                val mStudentObject1 =
                                    gson.fromJson(result, BaseClassReports::class.java)
                                System.out.println("vvh" + gson.toJson(mStudentObject1))
                                val benifisheryRowRecyclerviewAdapter =
                                    EntryReportsRecyclerviewAdapter(
                                        this@WelcomeActivityNewNext,
                                        mStudentObject1.master as ArrayList<MasterReports>,
                                        name
                                    )
                                recyclerView?.adapter = benifisheryRowRecyclerviewAdapter

                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                DialogUtil.stopProgressDisplay()
                                Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                    .setTitle("Server error,Please Try Again")
                                    .sneakError()
                            }
                        })
                    } else {

                        Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                            .setTitle(Constant.NO_INTERNET)
                            .sneakError()
                    }
                }*/
            }
            /* name.equals("Insurance4") -> {
                 branch?.visibility = View.GONE
                 bank?.visibility = View.VISIBLE
                 block?.visibility = View.GONE
                 district?.visibility = View.GONE
                 frameLayout?.visibility = View.VISIBLE

             }*/
        }
        recyclerView = findViewById(R.id.recyclerviewreports)

        recyclerView?.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        val scheme = resources.getStringArray(R.array.schemeArray)
        val sppiner_scheme = findViewById<Spinner>(R.id.sppiner_scheme)
        val adapter = ArrayAdapter(
            this,
            R.layout.spiner_row, scheme
        )
        sppiner_scheme.adapter = adapter

        sppiner_scheme.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {

                            if (DialogUtil.isConnectionAvailable(this@WelcomeActivityNewNext)) {
                                DialogUtil.displayProgress(this@WelcomeActivityNewNext)
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
                                    Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                        .addConverterFactory(
                                            ScalarsConverterFactory.create()
                                        ).client(client).build()
                                val apiServices = retrofit.create(ReportsEntryService::class.java)
                                val changePhotoResponseModelCall =
                                    apiServices.getReportsEntryService("Insurance1","")
                                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>) {
                                        DialogUtil.stopProgressDisplay()
                                        val gson = Gson()
                                        val fullResponse = response.body()
                                        val XmlString =
                                            fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                        val result = XmlString?.replace(("</string>").toRegex(), "")
                                        val mStudentObject1 =
                                            gson.fromJson(result, BaseClassReports::class.java)
                                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                                        val benifisheryRowRecyclerviewAdapter =
                                            EntryReportsRecyclerviewAdapter(
                                                this@WelcomeActivityNewNext,
                                                mStudentObject1.master as ArrayList<MasterReports>,
                                                name
                                            )
                                        recyclerView?.setAdapter(benifisheryRowRecyclerviewAdapter)

                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        DialogUtil.stopProgressDisplay()
                                        Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                            .setTitle("Server error,Please Try Again")
                                            .sneakError()
                                    }
                                })
                            } else {

                                Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                    .setTitle(Constant.NO_INTERNET)
                                    .sneakError()
                            }
                        }

                    1 -> {
                        schemeId = "1"

                        if (DialogUtil.isConnectionAvailable(this@WelcomeActivityNewNext)) {
                            DialogUtil.displayProgress(this@WelcomeActivityNewNext)
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
                                Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                    .addConverterFactory(
                                        ScalarsConverterFactory.create()
                                    ).client(client).build()
                            val apiServices = retrofit.create(ReportsEntryService::class.java)
                            val changePhotoResponseModelCall =
                                apiServices.getReportsEntryService(
                                    "Insurance1",
                                    schemeId.toString()
                                )
                            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    DialogUtil.stopProgressDisplay()
                                    val gson = Gson()
                                    val fullResponse = response.body()
                                    val XmlString =
                                        fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                    val result = XmlString?.replace(("</string>").toRegex(), "")
                                    val mStudentObject1 =
                                        gson.fromJson(result, BaseClassReports::class.java)
                                    System.out.println("vvh" + gson.toJson(mStudentObject1))
                                    val benifisheryRowRecyclerviewAdapter =
                                        EntryReportsRecyclerviewAdapter(
                                            this@WelcomeActivityNewNext,
                                            mStudentObject1.master as ArrayList<MasterReports>,
                                            name
                                        )
                                    recyclerView?.setAdapter(benifisheryRowRecyclerviewAdapter)

                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    DialogUtil.stopProgressDisplay()
                                    Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                        .setTitle("Server error,Please Try Again")
                                        .sneakError()
                                }
                            })
                        } else {

                            Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                .setTitle(Constant.NO_INTERNET)
                                .sneakError()
                        }
                    }
                    2 -> {
                        schemeId = "2"

                            if (DialogUtil.isConnectionAvailable(this@WelcomeActivityNewNext)) {
                                DialogUtil.displayProgress(this@WelcomeActivityNewNext)
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
                                    Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                        .addConverterFactory(
                                            ScalarsConverterFactory.create()
                                        ).client(client).build()
                                val apiServices = retrofit.create(ReportsEntryService::class.java)
                                val changePhotoResponseModelCall =
                                    apiServices.getReportsEntryService("Insurance1",schemeId.toString())
                                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        DialogUtil.stopProgressDisplay()
                                        val gson = Gson()
                                        val fullResponse = response.body()
                                        val XmlString =
                                            fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                        val result = XmlString?.replace(("</string>").toRegex(), "")
                                        val mStudentObject1 =
                                            gson.fromJson(result, BaseClassReports::class.java)
                                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                                        val benifisheryRowRecyclerviewAdapter =
                                            EntryReportsRecyclerviewAdapter(
                                                this@WelcomeActivityNewNext,
                                                mStudentObject1.master as ArrayList<MasterReports>,
                                                name
                                            )
                                        recyclerView?.setAdapter(benifisheryRowRecyclerviewAdapter)

                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        DialogUtil.stopProgressDisplay()
                                        Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                            .setTitle("Server error,Please Try Again")
                                            .sneakError()
                                    }
                                })
                            } else {

                                Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                    .setTitle(Constant.NO_INTERNET)
                                    .sneakError()
                            }

                    }
                    3 -> {
                        schemeId = "3"

                            if (DialogUtil.isConnectionAvailable(this@WelcomeActivityNewNext)) {
                                DialogUtil.displayProgress(this@WelcomeActivityNewNext)
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
                                    Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                        .addConverterFactory(
                                            ScalarsConverterFactory.create()
                                        ).client(client).build()
                                val apiServices = retrofit.create(ReportsEntryService::class.java)
                                val changePhotoResponseModelCall =
                                    apiServices.getReportsEntryService("Insurance1",schemeId.toString())
                                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        DialogUtil.stopProgressDisplay()
                                        val gson = Gson()
                                        val fullResponse = response.body()
                                        val XmlString =
                                            fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                        val result = XmlString?.replace(("</string>").toRegex(), "")
                                        val mStudentObject1 =
                                            gson.fromJson(result, BaseClassReports::class.java)
                                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                                        val benifisheryRowRecyclerviewAdapter =
                                            EntryReportsRecyclerviewAdapter(
                                                this@WelcomeActivityNewNext,
                                                mStudentObject1.master as ArrayList<MasterReports>,
                                                name
                                            )
                                        recyclerView?.setAdapter(benifisheryRowRecyclerviewAdapter)

                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        DialogUtil.stopProgressDisplay()
                                        Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                            .setTitle("Server error,Please Try Again")
                                            .sneakError()
                                    }
                                })
                            } else {

                                Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                    .setTitle(Constant.NO_INTERNET)
                                    .sneakError()
                            }

                    }
                    4 -> {
                        schemeId = "4"

                            if (DialogUtil.isConnectionAvailable(this@WelcomeActivityNewNext)) {
                                DialogUtil.displayProgress(this@WelcomeActivityNewNext)
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
                                    Retrofit.Builder().baseUrl(Constant.API_BASE_URL)
                                        .addConverterFactory(
                                            ScalarsConverterFactory.create()
                                        ).client(client).build()
                                val apiServices = retrofit.create(ReportsEntryService::class.java)
                                val changePhotoResponseModelCall =
                                    apiServices.getReportsEntryService("Insurance1",schemeId.toString())
                                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        DialogUtil.stopProgressDisplay()
                                        val gson = Gson()
                                        val fullResponse = response.body()
                                        val XmlString =
                                            fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                        val result = XmlString?.replace(("</string>").toRegex(), "")
                                        val mStudentObject1 =
                                            gson.fromJson(result, BaseClassReports::class.java)
                                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                                        val benifisheryRowRecyclerviewAdapter =
                                            EntryReportsRecyclerviewAdapter(
                                                this@WelcomeActivityNewNext,
                                                mStudentObject1.master as ArrayList<MasterReports>,
                                                name
                                            )
                                        recyclerView?.setAdapter(benifisheryRowRecyclerviewAdapter)

                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        DialogUtil.stopProgressDisplay()
                                        Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                            .setTitle("Server error,Please Try Again")
                                            .sneakError()
                                    }
                                })
                            } else {
                                Sneaker.with(this@WelcomeActivityNewNext) // Activity, Fragment or ViewGroup
                                    .setTitle(Constant.NO_INTERNET)
                                    .sneakError()
                            }

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }
}
