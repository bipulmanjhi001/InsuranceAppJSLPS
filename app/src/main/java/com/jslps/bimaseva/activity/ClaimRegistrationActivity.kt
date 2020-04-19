package com.jslps.bimaseva.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
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
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class ClaimRegistrationActivity : AppCompatActivity() {
    var linearLayout1: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    var linearLayout3: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_claim_registration)
        supportActionBar?.title = "Registration Type";
        linearLayout1 = findViewById(R.id.shgRegistration)
        linearLayout2 = findViewById(R.id.shgFamilyregistration)
        linearLayout3 = findViewById(R.id.registrationothers)
        linearLayout1?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent = Intent(

                    this@ClaimRegistrationActivity,
                    ClaimRegistrationActivitySHGMember::class.java
                )
                intent.putExtra("schemeID",schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout2?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent = Intent(
                    this@ClaimRegistrationActivity,
                    ClaimRegistrationActivityFamilySHGMember::class.java
                )
                intent.putExtra("schemeID",schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
        linearLayout3?.setOnClickListener {
            if (!TextUtils.isEmpty(schemeId)) {
                val intent =
                    Intent(
                        this@ClaimRegistrationActivity,
                        ClaimRegistrationActivityOthers::class.java
                    )
                intent.putExtra("schemeID",schemeId)
                startActivity(intent)
            } else
                Sneaker.with(this@ClaimRegistrationActivity) // Activity, Fragment or ViewGroup
                    .setTitle("Please Select Any Scheme")
                    .sneakError()
        }
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
                view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> {
                        return
                    }

                    1 -> {
                        schemeId = "1"

                    }
                    2 -> {
                        schemeId = "2"


                    }
                    3 -> {
                        schemeId = "3"


                    }
                    4 -> {
                        schemeId = "4"


                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    var schemeId: String? = null
}
