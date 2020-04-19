package com.jslps.bimaseva.ui.scheme

import Table1LoginDb
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.base.BasePresenter
import com.jslps.bimaseva.base.Presenter
import com.jslps.bimaseva.cache.AppCache
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.LoginPojo
import com.jslps.bimaseva.network.LoginService
import com.jslps.bimaseva.network.ServiceUpdateListner
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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


class SchemeDetailsPresenter(view: SchemeDetailsView, context: Activity) : BasePresenter,
    Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {
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
        when (itemId) {
            0 -> {
                AppCache.getCache().insuranceStep = "Registered"
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi(
                        "regproces", "0",
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
                                view?.gotoScreen(
                                    Constant.INSURANCE_LIST_FRAGMENT,
                                    mStudentObject1.Master
                                )
                            } else view?.showMessage("You don't have any insurance,Please Add it.")
                        } else {
                            view?.showMessage("You don't have any insurance,Please Add it.")
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
            1 -> {
                AppCache.getCache().insuranceStep = "Document ready but not received by the branch"
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi(
                        "DocumentNotUp", "1",
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
                                view?.gotoScreen(
                                    Constant.DOCUMENT_LIST_FRAGMENT,
                                    mStudentObject1.Master
                                )
                            } else view?.showMessage("You don't have any insurance,Please Add it.")
                        } else {
                            view?.showMessage("You don't have any insurance,Please Add it.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            2 -> {
                AppCache.getCache().insuranceStep = "Under Process"
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi(
                        "underproces", "0", getAppCache().insuranceStepSend!!,
                        arraylistPanchyat.get(0).blockcode!!
                    )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        DialogUtil.stopProgressDisplay()
                        val gson = Gson()
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                view?.gotoScreen(
                                    Constant.INSURANCE_LIST_FRAGMENT,
                                    mStudentObject1.Master
                                )
                            } else
                                view?.showMessage("You don't have any insurance,Please Add it.")
                        } else {
                            view?.showMessage("You don't have any insurance,Please Add it.")

                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            3 -> {
                AppCache.getCache().insuranceStep = "Claim Settled"
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi(
                        "cs", "0", getAppCache().insuranceStepSend!!,
                        arraylistPanchyat.get(0).blockcode!!
                    )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        DialogUtil.stopProgressDisplay()
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                view?.gotoScreen(
                                    Constant.INSURANCE_LIST_FRAGMENT,
                                    mStudentObject1.Master
                                )
                            } else view?.showMessage("You don't have any insurance,Please Add it.")
                        } else {
                            view?.showMessage("You don't have any insurance,Please Add it.")

                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            4 -> {
                AppCache.getCache().insuranceStep = "Rejected"
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi(
                        "rej", "2", getAppCache().insuranceStepSend!!,
                        arraylistPanchyat.get(0).blockcode!!
                    )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val gson = Gson()
                        Log.v("Response prof :", "hgfgfrhgs" + response.body())
                        DialogUtil.stopProgressDisplay()
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                view?.gotoScreen(
                                    Constant.INSURANCE_LIST_FRAGMENT,
                                    mStudentObject1.Master
                                )
                            } else view?.showMessage("You don't have any insurance,Please Add it.")
                        } else {
                            view?.showMessage("You don't have any insurance,Please Add it.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            5 -> {
                AppCache.getCache().insuranceStep = "Document False"
                DialogUtil.displayProgress(context!!)
                val changePhotoResponseModelCall =
                    apiServices.getTabletDownloadDataBCsakhi(
                        "Duplicate", "0", getAppCache().insuranceStepSend!!,
                        arraylistPanchyat.get(0).blockcode!!
                    )
                changePhotoResponseModelCall.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val gson = Gson()
                        DialogUtil.stopProgressDisplay()
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        val mStudentObject1 = gson.fromJson(result, LoginPojo::class.java)
                        System.out.println("vvh" + gson.toJson(mStudentObject1))
                        if (mStudentObject1 != null) {
                            if (mStudentObject1.Master.isNotEmpty()) {
                                AppCache.getCache().loginPojo = mStudentObject1 as LoginPojo
                                view?.gotoScreen(
                                    Constant.DOCUMENT_FALSE_LIST_FRAGMENT,
                                    mStudentObject1.Master
                                )
                            } else view?.showMessage("You don't have any insurance,Please Add it.")
                        } else {
                            view?.showMessage("You don't have any insurance,Please Add it.")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        val toast = Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })
            }
            6 -> {
                AppCache.getCache().insuranceStep = "Total Claim"
                view?.gotoScreen(Constant.REPORTS_DETAILS_FRAGMENT, "")
            }

        }

    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }

    var view: SchemeDetailsView? = view
    var context: Activity? = context
    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }

    override fun resume(insuranceName: List<Master>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resume() {
        val schemedata = ArrayList<String>()
        schemedata.add("Registered")
        schemedata.add("Document ready but not received by the branch")
        schemedata.add("Under Process")
        schemedata.add("Claim Settled")
        schemedata.add("Rejected")
        schemedata.add("Document False")
        schemedata.add("Total Claim")
        view?.loadData(schemedata)
    }

    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onSuccessResponse(request: ApiRequest, data: Any) {

    }

    override fun onFailureResponse(request: ApiRequest, data: Any) {
        view?.hideProgress()
        /*if (chkIntenrnetIssue(data.message))
            view?.noInternet()*/
        view?.loadData(null)
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {

    }

    fun getListner(): OnFragmentListItemSelectListener {
        return this
    }


}

