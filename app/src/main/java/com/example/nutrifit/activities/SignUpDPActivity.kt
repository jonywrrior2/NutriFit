package com.example.nutrifit.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R
import com.example.nutrifit.databinding.ActivitySignupdpBinding


class SignUpDPActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupdpBinding
    private lateinit var sexoSpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupdpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sexoSpinner = findViewById(R.id.sexoSpinner)

        val factorActividadAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_sexo,
            R.layout.spinner_item_layout
        )
        factorActividadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexoSpinner.adapter = factorActividadAdapter

        // Manejar la selección del Spinner de Factor de Actividad
        sexoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedFactor = parent.getItemAtPosition(position).toString()
                // Aquí puedes hacer algo con el factor de actividad seleccionado
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Manejar la situación en la que no se selecciona nada
            }
        }

    }
}