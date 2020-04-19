package com.jslps.bimaseva.ui.reports

import Table1LoginDb
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.base.BasePresenter
import com.jslps.bimaseva.base.Presenter
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.MasterX
import com.jslps.bimaseva.model.ReportModel
import com.jslps.bimaseva.network.LoginService
import com.jslps.bimaseva.network.ServiceUpdateListner
import com.google.gson.GsonBuilder
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.model.LoginPojo
import com.jslps.bimaseva.model.Master
import com.orm.query.Select
import com.twidpay.beta.model.ApiRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class SchemeDetailsPresenterReport(view: SchemeDetailsViewReport, context: Activity) :
    BasePresenter, Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {
        view?.gotoScreen(Constant.REPORT_LIST_FRAGMENT, data as String)
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    var view: SchemeDetailsViewReport? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }

    override fun resume(insuranceName: List<Master>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun resume() {
        val arraylistPanchyat: java.util.ArrayList<Table1LoginDb> =
            Select.from<Table1LoginDb>(
                Table1LoginDb::class.java
            ).list() as java.util.ArrayList<Table1LoginDb>
        DialogUtil.displayProgress(context!!)
        val gson = GsonBuilder().setLenient().create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        //comment in live build and uncomment in uat
        builder.interceptors().add(interceptor)
        builder.connectTimeout(180, TimeUnit.SECONDS)
        builder.readTimeout(180, TimeUnit.SECONDS)
        val client = builder.build()
        val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
            ScalarsConverterFactory.create()
        ).client(client).build()

        val apiServices = retrofit.create(LoginService::class.java)
        val changePhotoResponseModelCall = apiServices.getTabletDownloadDataBCsakhi(
            "reportreg", getAppCache().insuranceStepSend!!,
            arraylistPanchyat.get(0).blockcode!!, ""
        )
        changePhotoResponseModelCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                DialogUtil.stopProgressDisplay()

                val fullResponse = response.body()
                val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                val result = XmlString?.replace(("</string>").toRegex(), "")
                val mStudentObject1 = gson.fromJson(result, ReportModel::class.java)
                for (item in mStudentObject1.Master) {
                    mStudentObject1.Master.get(index = 0).Column2 = "Registered ("
                    mStudentObject1.Master.get(index = 1).Column2 =
                        "Document ready but not received by the branch ("
                    mStudentObject1.Master.get(index = 2).Column2 = "Under Process ("
                    mStudentObject1.Master.get(index = 3).Column2 = "Claim Settled ("
                    mStudentObject1.Master.get(index = 4).Column2 = "Rejected ("
                }
                System.out.println("vvh" + gson.toJson(mStudentObject1))
                view?.loadData(mStudentObject1.Master as ArrayList<MasterX>)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                DialogUtil.stopProgressDisplay()
                view?.showMessage("Server Error,Please Try Again")
            }
        })
    }

    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onSuccessResponse(request: ApiRequest, data: Any) {

    }

    override fun onFailureResponse(request: ApiRequest, data: Any) {
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {

    }

    fun getListner(): OnFragmentListItemSelectListener {
        return this
    }

    fun loadReportList(insuranceTypeFetch: String?) {
        val arraylistPanchyat: java.util.ArrayList<Table1LoginDb> =
            Select.from<Table1LoginDb>(
                Table1LoginDb::class.java
            ).list() as java.util.ArrayList<Table1LoginDb>
        val gson = GsonBuilder().setLenient().create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        //comment in live build and uncomment in uat
        builder.interceptors().add(interceptor)
        builder.connectTimeout(180, TimeUnit.SECONDS)
        builder.readTimeout(180, TimeUnit.SECONDS)
        val client = builder.build()
        val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
            ScalarsConverterFactory.create()
        ).client(client).build()
        val apiServices = retrofit.create(LoginService::class.java)
        when (insuranceTypeFetch) {
            "Registered" -> {
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall = apiServices.getTabletDownloadDataBCsakhi(
                    "regprocesrpt", "0",
                    getAppCache().insuranceStepSend.toString(),
                    arraylistPanchyat.get(0).blockcode!!
                )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                mStudentObject1.Master.get(0).insuranceTypeFetch="Registered"
                                view?.loadDataReport(mStudentObject1.Master as ArrayList<Master>)

                            } else view?.showMessage("You don't have any insurance.")
                        } else {
                            view?.showMessage("You don't have any insurance.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(
                            context,
                            "Server error please try again",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

            }
            "Document ready but not received by the branch" -> {
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall = apiServices.getTabletDownloadDataBCsakhi(
                    "DocumentNotUprpt", "0",
                    getAppCache().insuranceStepSend.toString(),
                    arraylistPanchyat.get(0).blockcode!!
                )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                mStudentObject1.Master.get(0).insuranceTypeFetch="Document ready but not received by the branch"
                                view?.loadDataReport(mStudentObject1.Master as ArrayList<Master>)
                            } else view?.showMessage("You don't have any insurance.")
                        } else {
                            view?.showMessage("You don't have any insurance.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(
                            context,
                            "Server error please try again",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

            }
            "Under Process" -> {
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall = apiServices.getTabletDownloadDataBCsakhi(
                    "underprocesrpt", "0",
                    getAppCache().insuranceStepSend.toString(),
                    arraylistPanchyat.get(0).blockcode!!
                )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                mStudentObject1.Master.get(0).insuranceTypeFetch="Under Process"
                                view?.loadDataReport(mStudentObject1.Master as ArrayList<Master>)
                            } else view?.showMessage("You don't have any insurance.")
                        } else {
                            view?.showMessage("You don't have any insurance.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(
                            context,
                            "Server error please try again",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

            }
            "Claim Settled" -> {
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall = apiServices.getTabletDownloadDataBCsakhi(
                    "csrpt", "0",
                    getAppCache().insuranceStepSend.toString(),
                    arraylistPanchyat.get(0).blockcode!!
                )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                mStudentObject1.Master.get(0).insuranceTypeFetch="Claim Settled"
                                view?.loadDataReport(mStudentObject1.Master as ArrayList<Master>)
                            } else view?.showMessage("You don't have any insurance.")
                        } else {
                            view?.showMessage("You don't have any insurance.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(
                            context,
                            "Server error please try again",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

            }
            "Rejected" -> {
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall = apiServices.getTabletDownloadDataBCsakhi(
                    "rejrpt", "2",
                    getAppCache().insuranceStepSend.toString(),
                    arraylistPanchyat.get(0).blockcode!!
                )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                mStudentObject1.Master.get(0).insuranceTypeFetch="Rejected"
                                view?.loadDataReport(mStudentObject1.Master as ArrayList<Master>)
                            } else view?.showMessage("You don't have any insurance.")
                        } else {
                            view?.showMessage("You don't have any insurance.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(
                            context,
                            "Server error please try again",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })

            }
        }
    }
}

