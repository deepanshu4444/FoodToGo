package com.example.foodtogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    lateinit var signup: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signup=findViewById(R.id.signup)
        signup.setOnClickListener {

            var intent = Intent(this@LoginActivity,register::class.java)
            startActivity(intent)
        }
    }
}
