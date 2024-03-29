package com.example.nutrifit.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.nutrifit.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var passText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView
    private lateinit var swRecordar: SwitchCompat
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var passOlvidada: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Referenciar componentes
        emailText = findViewById(R.id.emailText)
        passText = findViewById(R.id.passText)
        loginButton = findViewById(R.id.btnLogin)
        registerText = findViewById(R.id.registerText)
        swRecordar = findViewById(R.id.swRecordar)
        passOlvidada = findViewById(R.id.passOlvidada)

        // Verificar si el Switch está activado en SharedPreferences
        val recordar = sharedPreferences.getBoolean("recordar", false)
        swRecordar.isChecked = recordar

        // Rellenar automáticamente el correo y la contraseña si se recuerda
        if (recordar) {
            val correoGuardado = sharedPreferences.getString("correo", "")
            val contrasenaGuardada = sharedPreferences.getString("contrasena", "")

            emailText.setText(correoGuardado)
            passText.setText(contrasenaGuardada)
        }

        // Evento para ir a la actividad de registro
        registerText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpDPActivity::class.java)
            startActivity(intent)
        }

        passOlvidada.setOnClickListener {
            val intent = Intent(this@LoginActivity, RecuperarPassActivity::class.java)
            startActivity(intent)
        }

        // Evento de clic en el botón de inicio de sesión
        loginButton.setOnClickListener {
            val email = emailText.text.toString()
            val password = passText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Autenticar al usuario con Firebase Auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Guardar el correo y la contraseña en SharedPreferences si se recuerda
                            if (swRecordar.isChecked) {
                                sharedPreferences.edit().putString("correo", email).apply()
                                sharedPreferences.edit().putString("contrasena", password).apply()
                            }

                            // Redirigir a la actividad principal
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            // Error al iniciar sesión
                            showDialog("Error", "El email o la contraseña no son correctos.")
                        }
                    }
            } else {
                // Mostrar mensaje de error si los campos están vacíos
                showDialog("Error", "Por favor, completa todos los campos.")
            }
        }

        // Establecer un listener para el SwitchCompat
        swRecordar.setOnCheckedChangeListener { _, isChecked ->
            // Guardar el estado del Switch en SharedPreferences
            sharedPreferences.edit().putBoolean("recordar", isChecked).apply()
        }
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
