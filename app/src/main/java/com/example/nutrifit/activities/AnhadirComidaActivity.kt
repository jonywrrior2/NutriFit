package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R
import com.example.nutrifit.comidas.ComidasAdapter
import com.example.nutrifit.db.DatabaseManager
import com.example.nutrifit.pojo.Alimento
import com.google.android.material.textfield.TextInputEditText

class AnhadirComidaActivity : AppCompatActivity() {

    private lateinit var txtIngresarAlimento: TextInputEditText
    private lateinit var comidasRecyclerView: RecyclerView
    private lateinit var adapter: ComidasAdapter

    private var recyclerViewVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anhadircomida)

        // Inicializar views
        txtIngresarAlimento = findViewById(R.id.txtIngresarAlimento)
        comidasRecyclerView = findViewById(R.id.comidasRecyclerView)

        adapter = ComidasAdapter(this) { comidaSeleccionada ->
            txtIngresarAlimento.setText(comidaSeleccionada.nombre)
            openActivityNutrientes(comidaSeleccionada)

            comidasRecyclerView.visibility = View.GONE
            recyclerViewVisible = false
        }

        comidasRecyclerView.adapter = adapter
        comidasRecyclerView.layoutManager = LinearLayoutManager(this)

        txtIngresarAlimento.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                comidasRecyclerView.visibility = View.VISIBLE
                recyclerViewVisible = true

                obtenerComidasFiltradas(s.toString()) { alimentos ->
                    adapter.actualizarLista(alimentos)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun obtenerComidasFiltradas(query: String, callback: (List<Alimento>) -> Unit) {
        val databaseManager = DatabaseManager()

        databaseManager.buscarAlimentos(query) { alimentosEncontrados ->
            callback(alimentosEncontrados)
        }
    }

    fun volverMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun openActivityNutrientes(alimento: Alimento) {
        val intent = Intent(this, NutrientesActivity::class.java).apply {
            putExtra("nombre", alimento.nombre)
            putExtra("calorias", alimento.calorias)
            putExtra("proteinas", alimento.proteinas)
            putExtra("cantidad", alimento.cantidad)
        }
        startActivity(intent)
    }
}
