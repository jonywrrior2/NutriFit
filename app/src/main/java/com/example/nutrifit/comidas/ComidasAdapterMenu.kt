package com.example.nutrifit.comidas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R
import com.example.nutrifit.pojo.Menu

class ComidasAdapterMenu(private val context: Context, private var menus: List<Menu>) : RecyclerView.Adapter<ComidasAdapterMenu.ComidaViewHolder>() {

    inner class ComidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewAlimentos: TextView = itemView.findViewById(R.id.textViewAlimentos)
        val textViewCantidad: TextView = itemView.findViewById(R.id.textViewCantidad)
        val textViewKcal: TextView = itemView.findViewById(R.id.textViewKcal)
        val textViewProteinas: TextView = itemView.findViewById(R.id.textViewProteinas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_item_layout, parent, false)
        return ComidaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val currentItem = menus[position]
        holder.textViewAlimentos.text = context.getString(R.string.alimentos_template, currentItem.alimentos)
        holder.textViewCantidad.text = context.getString(R.string.cantidad_template, currentItem.cantidad)+ " ${currentItem.unidad}"
        holder.textViewKcal.text = context.getString(R.string.kcal_template, currentItem.kcal)+ "/kcal"
        holder.textViewProteinas.text = context.getString(R.string.proteinas_template, currentItem.proteinas) + " g"
    }

    override fun getItemCount() = menus.size

    fun actualizarLista(nuevosMenus: List<Menu>) {
        menus = nuevosMenus
        notifyDataSetChanged()
    }
}
