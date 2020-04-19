package com.jslps.bimaseva.activity

import CallCenter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.chaos.view.PinView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import com.jslps.bimaseva.Constant
import com.jslps.bimaseva.DialogUtil
import com.jslps.bimaseva.R
import com.jslps.bimaseva.network.InsuranceCreate
import com.jslps.bimaseva.network.InsuranceCreateOTP
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ClaimRegistrationActivityOtpScreen : AppCompatActivity() {
    private fun showTimer(countdownTimer: String?) {
        if (countdownTimer == null || countdownTimer.toLong() == 0L) {
            countDownTimer?.cancel()
            timer?.visibility = View.INVISIBLE
        } else {
            startTimer(countdownTimer)
        }
    }

    private fun startTimer(countdownTimer: String) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(countdownTimer.toLong() * 1000, 1000) {
            // adjust the milli seconds here

            override fun onTick(millisUntilFinished: Long) {
                timer?.visibility = View.VISIBLE
                timer?.text = " 0" + String.format(
                    "%d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
            }

            override fun onFinish() {
                timer?.visibility = View.INVISIBLE
                resendOtp?.visibility = View.VISIBLE
                button?.visibility = View.INVISIBLE
            }
        }.start()

    }

    var countDownTimer: CountDownTimer? = null
    var timer: TextView? = null
    var resendOtp: TextView? = null
    var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.claim_registration_otp_screen)
        val model: CallCenter = intent.getParcelableExtra("data")
        val preferences = getSharedPreferences("MyPrefInsuranceOTP", Context.MODE_PRIVATE)
        var value = preferences?.getString("otp", "")
        val pinView = findViewById<PinView>(R.id.pinView)
        button = findViewById<Button>(R.id.button)
        resendOtp = findViewById<TextView>(R.id.resendOtp)
        timer = findViewById<TextView>(R.id.timer)
        showTimer("30")
        resendOtp?.setOnClickListener {
            if (DialogUtil.isConnectionAvailable(this@ClaimRegistrationActivityOtpScreen)) {
                DialogUtil.displayProgress(this@ClaimRegistrationActivityOtpScreen)
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
                val createInsurance = apiServices.createInsurance(model.Phno_claim)
                createInsurance.enqueue(object : Callback<String> {
                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        DialogUtil.stopProgressDisplay()
                        button?.visibility = View.VISIBLE
                        val fullResponse = response.body()
                        val XmlString =
                            fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                        val result = XmlString?.replace(("</string>").toRegex(), "")

                        Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                            .setTitle("OTP sent successfully ")
                            .sneakSuccess()
                        value = result

                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        DialogUtil.stopProgressDisplay()
                        Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                            .setTitle("Server Error Please Try Again")
                            .sneakError()
                    }
                })
            } else {
                Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                    .setTitle(Constant.NO_INTERNET)
                    .sneakError()
            }
        }
        button?.setOnClickListener {
            if (pinView.text.toString().length == 4) {
                if (pinView.text.toString().equals(value)) {
                    val data = "{" + "\"CallCenter\"" + " : [" + Gson().toJson(model) + "] } "
                    if (DialogUtil.isConnectionAvailable(this@ClaimRegistrationActivityOtpScreen)) {
                        DialogUtil.displayProgress(this@ClaimRegistrationActivityOtpScreen)
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
                                response: Response<String>
                            ) {
                                DialogUtil.stopProgressDisplay()
                                val fullResponse = response.body()
                                val XmlString =
                                    fullResponse?.substring(fullResponse.indexOf("\">") + 2)
                                val result = XmlString?.replace(("</string>").toRegex(), "")
                                if (result.equals("\"1\"")) {
                                    Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                                        .setTitle("Insurance Create Successfully ")
                                        .sneakSuccess()
                                    val intent = Intent(
                                        this@ClaimRegistrationActivityOtpScreen,
                                        WelcomeActivity::class.java
                                    )

                                    Handler().postDelayed(object : Runnable {
                                        override fun run() {
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                            startActivity(intent)
                                        }
                                    }, 2000)

                                } else {
                                    Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                                        .setTitle("Please Try Again")
                                        .sneakError()
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                DialogUtil.stopProgressDisplay()
                                Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                                    .setTitle("Server Error Please Try Again")
                                    .sneakError()
                            }
                        })
                    } else {
                        Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                            .setTitle(Constant.NO_INTERNET)
                            .sneakError()
                    }
                } else {
                    Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                        .setTitle("Please enter valid OTP")
                        .sneakError()
                }

            } else {
                Sneaker.with(this@ClaimRegistrationActivityOtpScreen) // Activity, Fragment or ViewGroup
                    .setTitle("Please enter OTP")
                    .sneakError()
            }
        }
    }
}
