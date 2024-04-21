package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R
import com.example.nutrifit.comidas.ComidasAdapter
import com.example.nutrifit.comidas.ComidasAdapterMenu
import com.example.nutrifit.dbAlimentos.DatabaseManager
import com.example.nutrifit.dbMenus.DatabaseManagerMenu
import com.example.nutrifit.pojo.Alimento
import com.example.nutrifit.pojo.Menu
import com.google.android.material.textfield.TextInputEditText

class   AnhadirComidaActivity : AppCompatActivity() {

    private lateinit var txtIngresarAlimento: TextInputEditText
    private lateinit var comidasRecyclerView: RecyclerView
    private lateinit var adapter: ComidasAdapter
    private lateinit var listaComidaRecyclerView: RecyclerView
    private lateinit var adapterMenu: ComidasAdapterMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anhadircomida)

        // Inicializar views
        txtIngresarAlimento = findViewById(R.id.txtIngresarAlimento)
        comidasRecyclerView = findViewById(R.id.comidasRecyclerView)
        listaComidaRecyclerView = findViewById(R.id.listaComida)

        adapter = ComidasAdapter(this) { comidaSeleccionada ->
            txtIngresarAlimento.setText(comidaSeleccionada.nombre)
            openActivityNutrientes(comidaSeleccionada)
            comidasRecyclerView.visibility = View.GONE
        }

        adapterMenu = ComidasAdapterMenu(this, emptyList())

        comidasRecyclerView.adapter = adapter
        comidasRecyclerView.layoutManager = LinearLayoutManager(this)

        listaComidaRecyclerView.adapter = adapterMenu
        listaComidaRecyclerView.layoutManager = LinearLayoutManager(this)

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

        obtenerMenusDelUsuarioActual()
    }

    private fun actualizarComidasTextView(menus: List<Menu>) {
        val builder = StringBuilder()
        for (menu in menus) {
            builder.append("Alimentos: ${menu.alimentos}\n")
            builder.append("Cantidad: ${menu.cantidad}\n")
            builder.append("Kcal: ${menu.kcal}/kcal\n")
            builder.append("Proteínas: ${menu.proteinas} g\n")
        }
        adapterMenu.actualizarLista(menus)
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

    private fun obtenerMenusDelUsuarioActual() {
        DatabaseManagerMenu.getUserMenus(
            onSuccess = { menus ->
                // Obtener el tipo de comida del Intent
                val tipoComida = intent.getStringExtra("tipo")
                // Filtrar los menús según el tipo de comida especificado en el Intent
                val menusFiltrados = menus.filter { it.tipo.equals(tipoComida) }
                // Actualizar la interfaz de usuario con los menús filtrados
                actualizarComidasTextView(menusFiltrados)
            },
            onFailure = { exception ->
                // Manejar la falla en caso de error
                Log.e("DatabaseManagerMenu", "Error al obtener los menús del usuario actual: $exception")
            }
        )
    }
}
