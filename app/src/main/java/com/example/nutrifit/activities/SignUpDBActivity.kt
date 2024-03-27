package com.example.nutrifit.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R
import com.example.nutrifit.databinding.ActivitySignupdbBinding

class SignUpDBActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupdbBinding
    private lateinit var factorActividadSpinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupdbBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtener referencia al Spinner
        factorActividadSpinner = findViewById(R.id.factorActividadSpinner)

        // Crear un ArrayAdapter usando el array de strings definido en strings.xml
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.factor_actividad_values,
            R.layout.spinner_item_layout// layout spinner
        )

        // Especificar el layout a usar cuando la lista de opciones aparezca
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicar el adaptador al Spinner
        factorActividadSpinner.adapter = adapter

        // Manejar la selección del usuario
        factorActividadSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedFactor = parent.getItemAtPosition(position).toString()
                // TODO Aquí puedes hacer algo con el factor de actividad seleccionado
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //TODO Manejar la situación en la que no se selecciona nada
            }
        }
    }
}
