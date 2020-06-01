package com.example.foodtogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar


class register : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var mobile: EditText
    lateinit var delivery: EditText
    lateinit var register: Button
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    name = findViewById(R.id.mobile)
    email = findViewById(R.id.email)
    mobile = findViewById(R.id.mobile)
    delivery = findViewById(R.id.delivery)
    register = findViewById(R.id.register)
    toolbar=findViewById(R.id.toolbar)

    setUpToolbar()



    register.setOnClickListener()
    {
        val name = name.text.toString()
        val email = email.text.toString()
        val mobile = mobile.text.toString()
        val delivery = delivery.text.toString()

        val intent = Intent(this@register, register_detail::class.java)

        intent.putExtra("name", name)
        intent.putExtra("email", email)
        intent.putExtra("mobile", mobile)
        intent.putExtra("delivery", delivery)

        startActivity(intent)
    }

}
    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Register Yourself"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }


}
