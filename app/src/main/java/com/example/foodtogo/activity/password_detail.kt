package com.example.foodtogo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.foodtogo.R

class password_detail : AppCompatActivity() {

    lateinit var tmobile:TextView
    lateinit var temail:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_detail)

        tmobile=findViewById(R.id.tmobile)
        temail=findViewById(R.id.temail)


        if(intent!=null)
        {
            var mobile = intent.getStringExtra("fmobile")
            var email= intent.getStringExtra("femail")

            tmobile.text=mobile
            temail.text=email

        }
    }
}
