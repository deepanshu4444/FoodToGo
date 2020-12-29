package com.example.foodtogo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.foodtogo.R

class Forgot : AppCompatActivity() {

    lateinit var next:Button
    lateinit var forgotmobile:EditText
    lateinit var forgotemail:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        next=findViewById(R.id.next)
        forgotemail=findViewById(R.id.forgotemail)
        forgotmobile=findViewById(R.id.forgotmobile)

        next.setOnClickListener {
            var femail =forgotemail.text.toString()
            var fmobile=forgotmobile.text.toString()
            var intent = Intent(this@Forgot,
                password_detail::class.java)
            intent.putExtra("fmobile",fmobile)
            intent.putExtra("femail",femail)
            startActivity(intent)
        }
    }
}
