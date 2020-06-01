package com.example.foodtogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast


class register_detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_detail)
        if(intent!=null)
        {
            var name = intent.getStringExtra("name")
            var email= intent.getStringExtra("email")
            var mobile = intent.getStringExtra("mobile")
            var delivery = intent.getStringExtra("delivery")
            Toast.makeText(this@register_detail,name,Toast.LENGTH_SHORT).show()
            Toast.makeText(this@register_detail,email,Toast.LENGTH_SHORT).show()
            Toast.makeText(this@register_detail,mobile,Toast.LENGTH_SHORT).show()
            Toast.makeText(this@register_detail,delivery,Toast.LENGTH_SHORT).show()
        }
    }
}
