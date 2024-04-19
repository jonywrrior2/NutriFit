package com.example.nutrifit.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R
import com.example.nutrifit.comidas.ComidasAdapter
import com.example.nutrifit.db.DatabaseManager
import com.google.android.material.textfield.TextInputEditText

class AnhadirComidaActivity : AppCompatActivity() {

    private lateinit var txtIngresarAlimento: TextInputEditText
    private lateinit var comidasRecyclerView: RecyclerView
    private lateinit var adapter: ComidasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anhadircomida)

        // Inicializar views
        txtIngresarAlimento = findViewById(R.id.txtIngresarAlimento)
        comidasRecyclerView = findViewById(R.id.comidasRecyclerView)

        adapter = ComidasAdapter(this)
        comidasRecyclerView.adapter = adapter
        comidasRecyclerView.layoutManager = LinearLayoutManager(this)

        // Escuchar cambios en el TextInputEditText
        txtIngresarAlimento.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                obtenerComidasFiltradas(s.toString()) { alimentos ->
                    adapter.actualizarLista(alimentos)
                }
            }


            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun obtenerComidasFiltradas(query: String, callback: (List<String>) -> Unit) {
        val databaseManager = DatabaseManager()

        databaseManager.buscarAlimentos(query) { alimentosEncontrados ->
            callback(alimentosEncontrados)
        }
    }

}

