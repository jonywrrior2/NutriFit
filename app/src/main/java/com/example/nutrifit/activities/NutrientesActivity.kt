package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R

class NutrientesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrientes)

        val nombre = intent.getStringExtra("nombre") //bien
        val calorias = intent.getDoubleExtra("kcal", 0.0) //bien
        val proteinas = intent.getDoubleExtra("proteinas", 0.0) //bien
        val cantidad = intent.getDoubleExtra("cantidad", 0.0) //bien
        val unidad = intent.getStringExtra("unidad")

        var cantidadSeleccionada = cantidad


        Log.d("UnidadValor", "Valor de unidad: $unidad")

        val txtNombre = findViewById<TextView>(R.id.txtcomidaNutriente)

        val txtUnidad = findViewById<TextView>(R.id.txtUnidadUsada)

        val txtCalorias = findViewById<TextView>(R.id.txtCaloriasAN)

        val txtProteinas = findViewById<TextView>(R.id.txtProteinasAN)

        val pickerCantidad = findViewById<NumberPicker>(R.id.numberPickerCantidad)

        val minValue = 25
        val maxValue = 1000
        val step = 25
        val valueCount = ((maxValue - minValue) / step) + 1


        val displayValues = Array(valueCount) { i -> (minValue + i * step).toString() }


        pickerCantidad.displayedValues = displayValues

// Establecer el rango de valores mínimo y máximo
        pickerCantidad.minValue = 0
        pickerCantidad.maxValue = valueCount - 1


        val initialValue = (cantidad.toInt() - minValue) / step
        pickerCantidad.value = initialValue


        pickerCantidad.setOnValueChangedListener { _, _, newVal ->
            // Actualizar la cantidad seleccionada con el nuevo valor del NumberPicker
            cantidadSeleccionada = (minValue + newVal * step).toDouble()

            // Calcular las nuevas calorías y proteínas con la nueva cantidad
            val nuevasCalorias = (calorias * cantidadSeleccionada / 50).toInt()
            val nuevasProteinas = (proteinas * cantidadSeleccionada / 50).toInt()

            // Actualizar las TextViews con los nuevos valores de calorías y proteínas
            txtCalorias.text = "$nuevasCalorias kcal"
            txtProteinas.text = "$nuevasProteinas g"
        }

        pickerCantidad.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        txtNombre.text = nombre

        txtUnidad.text = unidad

        txtCalorias.text = calorias.toString()+"/kcal"

        txtProteinas.text = proteinas.toString()+" g"



    }


    fun volverComida(view: View) {
        val intent = Intent(this, AnhadirComidaActivity::class.java)
        startActivity(intent)
    }
}