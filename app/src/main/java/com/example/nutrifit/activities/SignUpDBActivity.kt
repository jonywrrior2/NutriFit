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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R
import com.example.nutrifit.databinding.ActivitySignupdbBinding
import com.example.nutrifit.pojo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpDBActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupdbBinding
    private lateinit var factorActividadSpinner: Spinner
    private lateinit var objetivoSpinner: Spinner
    private lateinit var alturaTxt: EditText
    private lateinit var pesoTxt: EditText
    private lateinit var btnVolver: Button
    private lateinit var btnRegistar: Button
    private lateinit var user: User

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupdbBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtener referencia a los componentes
        factorActividadSpinner = findViewById(R.id.factorActividadSpinner)
        alturaTxt = findViewById(R.id.alturaTxt)
        pesoTxt = findViewById(R.id.pesoTxt)
        btnVolver = findViewById(R.id.volverDP)
        btnRegistar = findViewById(R.id.signupbtn)

        val email = intent.getStringExtra("email")
        val nombre = intent.getStringExtra("nombre")
        val apellidos = intent.getStringExtra("apellidos")
        val contrasenha = intent.getStringExtra("contrasenha")
        val sexo = intent.getStringExtra("sexo")

        // boton para volver a la activity anterior de datos personales
        btnVolver.setOnClickListener {
            val intent = Intent(this@SignUpDBActivity, SignUpDPActivity::class.java)
            startActivity(intent)
        }

        btnRegistar.setOnClickListener {
            // Obtener los datos adicionales del usuario
            val edad = binding.edadTxt.text.toString().toIntOrNull() ?: 0
            val altura = binding.alturaTxt.text.toString().toDoubleOrNull() ?: 0.0
            val peso = binding.pesoTxt.text.toString().toDoubleOrNull() ?: 0.0
            val nivelActividad = binding.factorActividadSpinner.selectedItem.toString()
            val objetivo = binding.objetivoSpinner.selectedItem.toString()

            // Verificar si los campos están completos
            if (email != null && nombre != null && apellidos != null && contrasenha != null && sexo != null && edad > 0 && altura > 0 && peso > 0) {
                // Crear el objeto User con todos los datos
                val user = User(
                    nombre,
                    apellidos,
                    email,
                    contrasenha,
                    edad,
                    sexo,
                    altura,
                    peso,
                    nivelActividad,
                    objetivo
                )

                // Registrar y autenticar al usuario
                registrarYAutenticarUsuario(email, contrasenha, user)
            } else {
                Toast.makeText(
                    this@SignUpDBActivity,
                    "Por favor, completa todos los campos correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Crear un ArrayAdapter usando el array de strings definido en strings.xml para Factor de Actividad
        val factorActividadAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.factor_actividad_values,
            R.layout.spinner_item_layout
        )
        factorActividadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        factorActividadSpinner.adapter = factorActividadAdapter

        // Manejar la selección del Spinner de Factor de Actividad
        factorActividadSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
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
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
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

    private fun registrarYAutenticarUsuario(email: String, contrasenha: String, user: User) {
        // Registrar al usuario en Firebase Authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contrasenha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registro exitoso, ahora guardamos el usuario en Firestore
                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    if (firebaseUser != null) {
                        guardarUsuarioEnFirestore(user)
                    } else {
                        Toast.makeText(
                            this@SignUpDBActivity,
                            "Error: usuario actual nulo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Error en el registro
                    Toast.makeText(
                        this@SignUpDBActivity,
                        "Error al registrar el usuario: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun guardarUsuarioEnFirestore(usuario: User) {
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(usuario.email).set(usuario)
            .addOnSuccessListener {
                Toast.makeText(
                    this@SignUpDBActivity,
                    "Usuario registrado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                // Éxito al guardar el usuario en Firestore
                // Después de guardar el usuario en Firestore, puedes redirigir al usuario a otra actividad si es necesario
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@SignUpDBActivity,
                    "Error al registrar el usuario en Firestore: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                // Error al guardar el usuario en Firestore
            }
    }
}
