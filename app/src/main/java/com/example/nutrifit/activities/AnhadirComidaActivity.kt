package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R
import android.view.View
import android.widget.TextView

class AnhadirComidaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anhadircomida)

        val back: TextView = findViewById(R.id.volverMain)

        back.setOnClickListener {
            finish()
        }


    }




}