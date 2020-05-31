package com.example.foodtogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_splash)
        Handler().postDelayed({
            val startAct=Intent(this@SplashScreen,LoginActivity::class.java)
            startActivity(startAct)
        },2000)
    }
}




