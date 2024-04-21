package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R
import com.example.nutrifit.comidas.ComidasAdapter
import com.example.nutrifit.dbFB.DatabaseManager
import com.example.nutrifit.pojo.Alimento
import com.google.android.material.textfield.TextInputEditText

class AnhadirComidaActivity : AppCompatActivity() {

    private lateinit var txtIngresarAlimento: TextInputEditText
    private lateinit var comidasRecyclerView: RecyclerView
    private lateinit var adapter: ComidasAdapter
    private lateinit var comidastxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anhadircomida)



        // Inicializar views
        txtIngresarAlimento = findViewById(R.id.txtIngresarAlimento)
        comidasRecyclerView = findViewById(R.id.comidasRecyclerView)
        comidastxt = findViewById(R.id.comidasTextView)


        adapter = ComidasAdapter(this) { comidaSeleccionada ->
            txtIngresarAlimento.setText(comidaSeleccionada.nombre)
            openActivityNutrientes(comidaSeleccionada)
            comidasRecyclerView.visibility = View.GONE
        }

        comidasRecyclerView.adapter = adapter
        comidasRecyclerView.layoutManager = LinearLayoutManager(this)

        txtIngresarAlimento.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                comidasRecyclerView.visibility = View.VISIBLE

                obtenerComidasFiltradas(s.toString()) { alimentos ->
                    adapter.actualizarLista(alimentos)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Recibir datos de NutrientesActivity
        val nombre = intent.getStringExtra("comidaNutriente")
        val cantidad = intent.getDoubleExtra("cantidad", 0.0)
        val calorias = intent.getDoubleExtra("calorias", 0.0)
        val proteinas = intent.getDoubleExtra("proteinas", 0.0)


        // Si los datos son diferentes de null, actualizar el texto del TextInputEditText
        if (nombre != null) {
            comidastxt.text = "Nombre: $nombre\nCalorías: $calorias kcal\nProteínas: $proteinas g\nCantidad: $cantidad"

        }
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
            putExtra("unidad", alimento.unidad)
        }
        startActivity(intent)
    }
}
