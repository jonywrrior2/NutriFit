package com.example.nutrifit.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrifit.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var passText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Referenciar componentes
        emailText = findViewById(R.id.emailText)
        passText = findViewById(R.id.passText)
        loginButton = findViewById(R.id.btnLogin)
        registerText = findViewById(R.id.registerText)

        // Evento para ir a la actividad de registro
        registerText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpDPActivity::class.java)
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
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
