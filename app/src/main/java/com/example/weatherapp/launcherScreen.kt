package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper


class launcherScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher_screen)

        Handler(Looper.getMainLooper()).postDelayed({
             var intent = Intent(this, getStarted::class.java)
            startActivity(intent)
            finish()
        },2000)
    }






}


