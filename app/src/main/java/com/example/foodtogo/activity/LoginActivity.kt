package com.example.foodtogo.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodtogo.R
import com.example.foodtogo.util.ConnectionManager
import com.example.foodtogo.util.SessionManager
import com.example.foodtogo.util.Validations
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var mobile:EditText
    lateinit var password:EditText
    lateinit var forgot:TextView
    lateinit var signup: TextView
    lateinit var login:Button

    lateinit var sharedPreferences: SharedPreferences
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mobile=findViewById(R.id.mobile)
        password=findViewById(R.id.password)
        forgot=findViewById(R.id.forgot)
        login=findViewById(R.id.login)
        signup=findViewById(R.id.signup)

        sessionManager = SessionManager(this)
        sharedPreferences =
            this.getSharedPreferences(sessionManager.PREF_NAME, sessionManager.PRIVATE_MODE)


        signup.setOnClickListener {

            var intent = Intent(this@LoginActivity,
                register::class.java)
            startActivity(intent)
        }


        forgot.setOnClickListener {
            var intent = Intent(this@LoginActivity,
                Forgot::class.java)
            startActivity(intent)
        }


        login.setOnClickListener {
            login.visibility = View.INVISIBLE

            if (Validations.validateMobile(mobile.text.toString()) && Validations.validatePasswordLength(password.text.toString())) {
                if (ConnectionManager().checkconnectivity(this@LoginActivity)) {


                    val queue = Volley.newRequestQueue(this@LoginActivity)


                    val jsonParams = JSONObject()
                    jsonParams.put("mobile_number", mobile.text.toString())
                    jsonParams.put("password", password.text.toString())



                    val jsonObjectRequest = object : JsonObjectRequest(
                        Method.POST, "http://13.235.250.119/v2/login/fetch_result/", jsonParams,
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
                                            this@LoginActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                } else {
                                    login.visibility = View.VISIBLE
                                    val errorMessage = data.getString("errorMessage")
                                    Toast.makeText(
                                        this@LoginActivity,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: JSONException) {
                                login.visibility = View.VISIBLE

                                e.printStackTrace()
                            }
                        },
                        Response.ErrorListener {
                            login.visibility = View.VISIBLE
                            Log.e("Error::::", "/post request fail! Error: ${it.message}")
                        }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"

                            /*The below used token will not work, kindly use the token provided to you in the training*/
                            headers["token"] = "14a430e270b218"
                            return headers
                        }
                    }
                    queue.add(jsonObjectRequest)

                } else {
                    login.visibility = View.VISIBLE
                    Toast.makeText(this@LoginActivity, "No internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                login.visibility = View.VISIBLE
                Toast.makeText(this@LoginActivity, "Invalid Phone or Password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onPause() {
        super.onPause()
        finish()
    }
}
