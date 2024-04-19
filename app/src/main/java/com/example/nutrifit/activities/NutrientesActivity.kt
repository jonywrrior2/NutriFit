package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R

class NutrientesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrientes)

        val nombre = intent.getStringExtra("nombre")
        val calorias = intent.getDoubleExtra("calorias", 0.0)
        val proteinas = intent.getDoubleExtra("proteinas", 0.0)
        val cantidad = intent.getDoubleExtra("cantidad", 0.0)

        val txtNombre = findViewById<TextView>(R.id.txtcomidaNutriente)

        txtNombre.text = nombre
    }


    fun volverComida(view: View) {
        val intent = Intent(this, AnhadirComidaActivity::class.java)
        startActivity(intent)
    }
}