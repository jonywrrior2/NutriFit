package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R

class InicioActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@InicioActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}