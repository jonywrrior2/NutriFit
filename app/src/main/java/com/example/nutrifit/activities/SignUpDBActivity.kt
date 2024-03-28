package com.example.nutrifit.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R
import com.example.nutrifit.databinding.ActivitySignupdbBinding

class SignUpDBActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupdbBinding
    private lateinit var factorActividadSpinner: Spinner
    private lateinit var objetivoSpinner: Spinner
    private lateinit var alturaTxt: EditText
    private lateinit var pesoTxt: EditText


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupdbBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtener referencia al Spinner de Factor de Actividad
        factorActividadSpinner = findViewById(R.id.factorActividadSpinner)
        alturaTxt = findViewById(R.id.alturaTxt)
        pesoTxt = findViewById(R.id.pesoTxt)

        // Crear un ArrayAdapter usando el array de strings definido en strings.xml para Factor de Actividad
        val factorActividadAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.factor_actividad_values,
            R.layout.spinner_item_layout
        )
        factorActividadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        factorActividadSpinner.adapter = factorActividadAdapter

        // Manejar la selección del Spinner de Factor de Actividad
        factorActividadSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedFactor = parent.getItemAtPosition(position).toString()
                // Aquí puedes hacer algo con el factor de actividad seleccionado
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Manejar la situación en la que no se selecciona nada
            }
        }

        // Obtener referencia al Spinner de Objetivo
        objetivoSpinner = findViewById(R.id.objetivoSpinner)

        // Crear un ArrayAdapter usando el array de strings definido en strings.xml para Objetivo
        val objetivoAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_objetivo,
            R.layout.spinner_item_layout
        )
        objetivoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        objetivoSpinner.adapter = objetivoAdapter

        // Manejar la selección del Spinner de Objetivo
        objetivoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedObjetivo = parent.getItemAtPosition(position).toString()
                // Aquí puedes hacer algo con el objetivo seleccionado
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Manejar la situación en la que no se selecciona nada
            }
        }

        // Agregar OnTouchListener al EditText de altura
        alturaTxt.setOnTouchListener { view, event ->
            // Verificar si se hizo clic en el icono de información
            if (event.action == MotionEvent.ACTION_UP && event.rawX >= (alturaTxt.right - alturaTxt.compoundDrawables[2].bounds.width())) {
                // Si el clic se encuentra dentro del área del icono de información
                showDialog("Altura (en cm)", "Introduce tu altura en centímetros.")
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }

        pesoTxt.setOnTouchListener { view, event ->
            // Verificar si se hizo clic en el icono de información
            if (event.action == MotionEvent.ACTION_UP && event.rawX >= (pesoTxt.right - pesoTxt.compoundDrawables[2].bounds.width())) {
                // Si el clic se encuentra dentro del área del icono de información
                showDialog("Peso (en kg)", "Introduce tu peso en kilogramos.")
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    // Función para mostrar el diálogo
    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
