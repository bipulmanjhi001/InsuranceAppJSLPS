package com.jslps.bimaseva.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.jslps.bimaseva.R

class WelcomeActivityNew : AppCompatActivity() {
    var linearLayout1: LinearLayout? = null
    var linearLayout2: LinearLayout? = null
    var linearLayout3: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_new)
        supportActionBar?.title = "Report Type";
        linearLayout1 = findViewById(R.id.layout1)
        linearLayout2 = findViewById(R.id.layout2)
        linearLayout3 = findViewById(R.id.layout3)
        linearLayout1?.setOnClickListener {
            val intent = Intent(this@WelcomeActivityNew, WelcomeActivityNewNext::class.java)
            intent.putExtra("clicktype", "Insurance2")
            startActivity(intent)
        }
        linearLayout2?.setOnClickListener {
            val intent = Intent(this@WelcomeActivityNew, WelcomeActivityNewNext::class.java)
            intent.putExtra("clicktype", "Insurance3")
            startActivity(intent)
        }
        linearLayout3?.setOnClickListener {
            val intent = Intent(this@WelcomeActivityNew, WelcomeActivityNewNext::class.java)
            intent.putExtra("clicktype", "Insurance1")
            startActivity(intent)
        }

    }
}
