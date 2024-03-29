package com.example.nutrifit.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R
import com.example.nutrifit.databinding.ActivitySignupdpBinding
import com.example.nutrifit.pojo.User
import com.google.firebase.firestore.FirebaseFirestore


class SignUpDPActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupdpBinding
    private lateinit var sexoSpinner: Spinner
    private lateinit var btnVolver: Button
    private lateinit var btnContinuar: Button
    private lateinit var contrasenha: EditText
    private lateinit var user: User

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupdpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //referencia componentes
        btnVolver = findViewById(R.id.volverLG)
        sexoSpinner = findViewById(R.id.sexoSpinner)
        btnContinuar = findViewById(R.id.continuarDB)
        contrasenha = findViewById(R.id.password)

        //evento para volver a la activity de login
        btnVolver.setOnClickListener {
            val intent = Intent(this@SignUpDPActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnContinuar.setOnClickListener {
            val email = binding.email.text.toString()
            val nombre = binding.nombre.text.toString()
            val apellidos = binding.apellidos.text.toString()
            val contrasenha = binding.password.text.toString()
            val sexo = binding.sexoSpinner.selectedItem.toString()

            if (email.isNotEmpty() && nombre.isNotEmpty() && apellidos.isNotEmpty() && contrasenha.isNotEmpty()) {
                if (contrasenha.length>6) {
                    val intent = Intent(this@SignUpDPActivity, SignUpDBActivity::class.java)
                    // Pasar los datos a la siguiente actividad
                    intent.putExtra("email", email)
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("apellidos", apellidos)
                    intent.putExtra("contrasenha", contrasenha)
                    intent.putExtra("sexo", sexo)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpDPActivity, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignUpDPActivity, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }


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


    private fun guardarUsuarioEnFirestore(usuario: User) {
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(usuario.email).set(usuario)
            .addOnSuccessListener {
                // Éxito al guardar el usuario en Firestore
            }
            .addOnFailureListener { e ->
                // Error al guardar el usuario en Firestore
            }
    }

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