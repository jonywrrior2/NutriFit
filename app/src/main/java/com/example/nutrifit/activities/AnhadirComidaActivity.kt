package com.example.nutrifit.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
import java.time.LocalDate

class   AnhadirComidaActivity : AppCompatActivity() {

    private lateinit var txtIngresarAlimento: TextInputEditText
    private lateinit var comidasRecyclerView: RecyclerView
    private lateinit var adapter: ComidasAdapter
    private lateinit var listaComidaRecyclerView: RecyclerView
    private lateinit var adapterMenu: ComidasAdapterMenu
    var tipo: String? = null
    var selectedLongClickDateStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anhadircomida)

        tipo = intent.getStringExtra("tipo")


        selectedLongClickDateStr = intent.getStringExtra("fechaSeleccionada")
        val selectedLongClickDate: LocalDate? = selectedLongClickDateStr?.let { LocalDate.parse(it) }


        // Inicializar views
        txtIngresarAlimento = findViewById(R.id.txtIngresarAlimento)
        comidasRecyclerView = findViewById(R.id.comidasRecyclerView)
        listaComidaRecyclerView = findViewById(R.id.listaComida)

        adapter = ComidasAdapter(this) { comidaSeleccionada ->
            txtIngresarAlimento.setText(comidaSeleccionada.nombre)
            openActivityNutrientes(comidaSeleccionada, tipo)
            comidasRecyclerView.visibility = View.GONE
        }

        adapterMenu = ComidasAdapterMenu(this, emptyList(), this)




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

        obtenerMenusDelUsuarioActual(selectedLongClickDate)
    }

    private fun actualizarComidasTextView(menus: List<Menu>) {
        val builder = StringBuilder()
        for (menu in menus) {
            builder.append("Alimentos: ${menu.alimentos}\n")
            builder.append("Cantidad: ${menu.cantidad}+ ${menu.unidad}\n")
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

    fun openActivityNutrientes(alimento: Alimento, tipo: String?) {
        val intent = Intent(this, NutrientesActivity::class.java).apply {
            putExtra("nombre", alimento.nombre)
            putExtra("calorias", alimento.calorias)
            putExtra("proteinas", alimento.proteinas)
            putExtra("cantidad", alimento.cantidad)
            putExtra("unidad", alimento.unidad)
            putExtra("tipo", tipo)
            putExtra("fechaSeleccionada", selectedLongClickDateStr)
        }
        startActivity(intent)


    }


     fun obtenerMenusDelUsuarioActual(selectedDate: LocalDate?) {
        val tipoComida = intent.getStringExtra("tipo")
        DatabaseManagerMenu.getUserMenus(
            onSuccess = { menus ->
                // Filtrar los menús por tipo de comida y fecha seleccionada
                val menusFiltrados = menus.filter { it.tipo == tipoComida && it.fecha == selectedDate?.toString() }

                // Actualizar la vista con los menús filtrados
                actualizarComidasTextView(menusFiltrados)
            },
            onFailure = { exception ->
                Log.e("DatabaseManagerMenu", "Error al obtener los menús del usuario actual: $exception")
            }
        )
    }
}
