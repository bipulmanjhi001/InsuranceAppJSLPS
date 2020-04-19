package com.jslps.bimaseva.ui.documentNotReady

import android.app.Activity
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.base.BasePresenter
import com.jslps.bimaseva.base.Presenter
import com.jslps.bimaseva.listener.OnFragmentListItemSelectListener
import com.jslps.bimaseva.model.Master
import com.jslps.bimaseva.model.UploadRegisterData
import com.jslps.bimaseva.network.ServiceUpdateListner
import com.jslps.bimaseva.network.UploadRegisterDocument
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.twidpay.beta.model.ApiRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


class DocumentNotReadyPresenter(view: DocumentNotReadyView, context: Activity) : BasePresenter,
    Presenter(),
    OnFragmentListItemSelectListener {
    override fun onListItemSelected(itemId: Int, data: Any) {
        view?.gotoScreen(Constant.Document_DETAILS_FRAGMRNT, data as Master)
    }

    override fun onListItemLongClicked(itemId: Int, data: Any) {
    }


    var view: DocumentNotReadyView? = view
    var context: Activity? = context

    override fun init() {
        ServiceUpdateListner.getInstance().setListener(this)
    }


    override fun resume(insuranceName: List<Master>?) {
        val master = getAppCache().loginPojo?.Master as ArrayList<Master>
        view?.loadData(master)
    }

    override fun resume() {

    }

    override fun onDestroy() {

    }

    override fun onPause() {

    }

    override fun onSuccessResponse(request: ApiRequest, data: Any) {

    }

    override fun onFailureResponse(request: ApiRequest, data: Any) {
        view?.hideProgress()
        view?.loadData(null)
    }

    override fun onFragmentInteraction(fragmentId: Int, data: Any?) {

    }

    fun getListner(): OnFragmentListItemSelectListener {
        return this
    }

    fun uploadRegisterDocument(insuranceNameeee: Master?, encodedBase64: String?) {

        if (DialogUtil.isConnectionAvailable(context)) {
            DialogUtil.displayProgress(context!!)
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(180, TimeUnit.SECONDS)
            builder.readTimeout(180, TimeUnit.SECONDS)
            val client = builder.build()
            val retrofit = Retrofit.Builder().baseUrl(Constant.API_BASE_URL).addConverterFactory(
                ScalarsConverterFactory.create()).client(client).build()
            val apiServices = retrofit.create(UploadRegisterDocument::class.java)
            val id = UUID.randomUUID().toString()
            val uploadRegisterData = UploadRegisterData(
                insuranceNameeee?.call_Id.toString(),
                insuranceNameeee?.createdBy.toString(),
                id,
                encodedBase64.toString(),
                insuranceNameeee?.createdOn.toString(),
                "1",
                "",
                "0",
                "",
                "",
                "0",
                "",
                "",
                "1",
                "",
                "0",
                ""
            )
            val data = "{" + "\"InsuranceImages\"" + " : [" + Gson().toJson(uploadRegisterData) + "] }"
            val changePhotoResponseModelCall =
                apiServices.uploadRegistedinsurance(data)
            changePhotoResponseModelCall.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        DialogUtil.stopProgressDisplay()
                        val fullResponse = response.body()
                        val XmlString = fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")
                        var jsonObj: JSONObject? = null
                        try {
                            jsonObj = JSONObject(result.toString())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        try {
                            val categoryObject = jsonObj?.getJSONArray("Table")
                            val jsonObject = categoryObject?.getJSONObject(0)
                            val Result = jsonObject?.getString("RetValue")
                            if (Result.equals("1", ignoreCase = true)) {
                                view?.showMessage("Insurance Update Successfully")
                            } else {
                                view?.showMessage("Please try again")
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        DialogUtil.stopProgressDisplay()
                        view?.showMessage("Server Error,Please Try Again")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    DialogUtil.stopProgressDisplay()
                    view?.showMessage("Server Error,Please Try Again")
                }
            })
        } else {
            view?.noInternet()
        }
    }
}

