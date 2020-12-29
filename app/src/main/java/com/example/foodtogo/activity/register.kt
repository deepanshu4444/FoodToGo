package com.example.foodtogo.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodtogo.R
import com.example.foodtogo.util.ConnectionManager
import com.example.foodtogo.util.SessionManager
import com.example.foodtogo.util.Validations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import java.lang.Exception


class register : AppCompatActivity() {

    lateinit var ename: EditText
    lateinit var eemail: EditText
    lateinit var emobile: EditText
    lateinit var epassword : EditText
    lateinit var econfirm : EditText
    lateinit var edelivery: EditText
    lateinit var register: Button
    lateinit var toolbar: Toolbar
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

        sessionManager = SessionManager(this@register)
        sharedPreferences = this@register.getSharedPreferences(sessionManager.PREF_NAME, sessionManager.PRIVATE_MODE)

    ename = findViewById(R.id.name)
    eemail = findViewById(R.id.email)
    emobile = findViewById(R.id.mobile)
   edelivery = findViewById(R.id.delivery)
    register = findViewById(R.id.register)
        epassword=findViewById(R.id.password)
        econfirm=findViewById(R.id.cpassword)
    toolbar=findViewById(R.id.toolbar)

    setUpToolbar()


    register.setOnClickListener()
    {
        val name = ename.text.toString()
        val email = eemail.text.toString()
        val password =epassword.text.toString()
        val mobile = emobile.text.toString()
        val confirm = econfirm.text.toString()
        val delivery = edelivery.text.toString()




        if (Validations.validateNameLength(name)) {
            ename.error = null
            if (Validations.validateEmail(email)) {
                eemail.error = null
                if (Validations.validateMobile(mobile)) {
                    emobile.error = null
                    if (Validations.validatePasswordLength(password)) {
                        epassword.error = null
                        if (Validations.matchPassword(
                                password,
                                confirm
                            )
                        ) {
                            epassword.error = null
                            econfirm.error = null
                            if (ConnectionManager().checkconnectivity(this@register)) {
                                sendRegisterRequest(
                                    name,
                                    mobile,
                                    delivery,
                                    password,
                                    email
                                )
                            } else {
                                Toast.makeText(this@register, "No Internet Connection", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            epassword.error = "Passwords don't match"
                            econfirm.error = "Passwords don't match"
                            Toast.makeText(this@register, "Passwords don't match", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        epassword.error = "Password should be more than or equal 4 digits"
                        Toast.makeText(
                            this@register,
                            "Password should be more than or equal 4 digits",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {

                    emobile.error = "Invalid Mobile number"
                    Toast.makeText(this@register, "Invalid Mobile number", Toast.LENGTH_SHORT).show()
                }
            } else {

                eemail.error = "Invalid Email"
                Toast.makeText(this@register, "Invalid Email", Toast.LENGTH_SHORT).show()
            }
        } else {
            ename.error = "Invalid Name"
            Toast.makeText(this@register, "Invalid Name", Toast.LENGTH_SHORT).show()
        }
    }

    }

    private fun sendRegisterRequest(name: String, phone: String, address: String, password: String, email: String) {

        val queue = Volley.newRequestQueue(this)

        val jsonParams = JSONObject()
        jsonParams.put("name", name)
        jsonParams.put("mobile_number", phone)
        jsonParams.put("password", password)
        jsonParams.put("address", address)
        jsonParams.put("email", email)

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST,
                    "http://13.235.250.119/v2/register/fetch_result",
            jsonParams,
            Response.Listener {
                try {
                    val data = it.getJSONObject("data")
                    val success = data.getBoolean("success")
                    if (success) {
                        val response = data.getJSONObject("data")
                        sharedPreferences.edit()
                            .putString("user_id", response.getString("user_id")).apply()
                        sharedPreferences.edit()
                            .putString("user_name", response.getString("name")).apply()
                        sharedPreferences.edit()
                            .putString(
                                "user_mobile_number",
                                response.getString("mobile_number")
                            )
                            .apply()
                        sharedPreferences.edit()
                            .putString("user_address", response.getString("address"))
                            .apply()
                        sharedPreferences.edit()
                            .putString("user_email", response.getString("email")).apply()
                        sessionManager.setLogin(true)
                        startActivity(
                            Intent(
                                this@register,
                                MainActivity::class.java
                            )
                        )
                        finish()
                    } else {

                        val errorMessage = data.getString("errorMessage")
                        Toast.makeText(
                            this@register,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this@register, it.message, Toast.LENGTH_SHORT).show()

            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"

                /*The below used token will not work, kindly use the token provided to you in the training*/
                headers["token"] = "14a430e270b218"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }



    override fun onSupportNavigateUp(): Boolean {
        Volley.newRequestQueue(this).cancelAll(this::class.java.simpleName)
        onBackPressed()
        return true
    }

    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Register Yourself"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }



}
