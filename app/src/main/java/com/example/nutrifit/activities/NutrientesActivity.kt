package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R

class NutrientesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrientes)
    }


    fun volverComida(view: View) {
        val intent = Intent(this, AnhadirComidaActivity::class.java)
        startActivity(intent)
    }
}