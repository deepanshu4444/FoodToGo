package com.example.foodtogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.foodtogo.register
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {
lateinit var name: EditText
    lateinit var email: EditText
    lateinit var mobile: EditText
    lateinit var delivery: EditText
lateinit var register :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

            name = findViewById(R.id.mobile)
        email = findViewById(R.id.email)
        mobile = findViewById(R.id.mobile)
        delivery = findViewById(R.id.delivery)
        register = findViewById(R.id.register)
        val name  = name.text.toString()
        val email  = email.text.toString()
        val mobile  = mobile.text.toString()
        val delivery  = delivery.text.toString()

        register.setOnClickListener {
    val intent = Intent(this@register,register_detail::class.java)
    startActivity(intent)
            intent.putExtra("name","name")
            intent.putExtra("email","email")
            intent.putExtra("mobile","mobile")
            intent.putExtra("delivery","delivery")

        }
    }


}
