package com.capstone.miemo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.miemo.MainActivity
import com.capstone.miemo.R

class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val splashScreenDuration = 2500L
        Thread(Runnable {
            Thread.sleep(splashScreenDuration)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }).start()

    }
}