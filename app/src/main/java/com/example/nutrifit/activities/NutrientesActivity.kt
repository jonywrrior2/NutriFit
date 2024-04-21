package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R

class NutrientesActivity : AppCompatActivity() {

    private lateinit var guardarAlimentoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrientes)

        val nombre = intent.getStringExtra("nombre")
        var calorias = intent.getDoubleExtra("kcal", 0.0)
        var proteinas = intent.getDoubleExtra("proteinas", 0.0)
        var cantidad = intent.getDoubleExtra("cantidad", 0.0)
        val unidad = intent.getStringExtra("unidad")

        var cantidadSeleccionada = cantidad
        var basec = calorias
        var basep = proteinas

        val txtNombre = findViewById<TextView>(R.id.txtcomidaNutriente)
        val txtUnidad = findViewById<TextView>(R.id.txtUnidadUsada)
        val txtCalorias = findViewById<TextView>(R.id.txtCaloriasAN)
        val txtProteinas = findViewById<TextView>(R.id.txtProteinasAN)
        val pickerCantidad = findViewById<NumberPicker>(R.id.numberPickerCantidad)
        guardarAlimentoButton = findViewById(R.id.guardarAlimento)

        guardarAlimentoButton.setOnClickListener {
            // Obtener los datos de los elementos TextView y NumberPicker
            val comidaNutriente = txtNombre.text.toString()
            cantidad = cantidadSeleccionada
            calorias = txtCalorias.text.toString().split(" ")[0].toDouble()
            proteinas = txtProteinas.text.toString().split(" ")[0].toDouble()

            // Crear un Intent para pasar los datos a AnhadirComidaActivity
            val intent = Intent(this@NutrientesActivity, AnhadirComidaActivity::class.java).apply {
                putExtra("comidaNutriente", comidaNutriente)
                putExtra("cantidad", cantidad)
                putExtra("calorias", calorias)
                putExtra("proteinas", proteinas)
            }

            // Iniciar la actividad AnhadirComidaActivity
            startActivity(intent)
        }

        val minValue = 25
        val maxValue = 1000
        val step = 25
        val valueCount = ((maxValue - minValue) / step) + 1
        val displayValues = Array(valueCount) { i -> (minValue + i * step).toString() }
        pickerCantidad.displayedValues = displayValues
        pickerCantidad.minValue = 0
        pickerCantidad.maxValue = valueCount - 1

        val initialValue = (cantidad.toInt() - minValue) / step
        pickerCantidad.value = initialValue

        pickerCantidad.setOnValueChangedListener { _, _, newVal ->
            cantidadSeleccionada = (minValue + newVal * step).toDouble()
            val calorias = (basec * cantidadSeleccionada / 50)
            val proteinas = (basep * cantidadSeleccionada / 50)
            txtCalorias.text = "$calorias kcal"
            txtProteinas.text = "$proteinas g"

        }

        pickerCantidad.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        txtNombre.text = nombre
        txtUnidad.text = unidad
        txtCalorias.text = "$calorias / kcal"
        txtProteinas.text = "$proteinas g"
    }

    fun volverComida(view: View) {
        val intent = Intent(this, AnhadirComidaActivity::class.java)
        startActivity(intent)
    }
}
