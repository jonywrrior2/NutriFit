package com.example.nutrifit.comidas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrifit.R

class ComidasAdapter(private val context: Context) : RecyclerView.Adapter<ComidasAdapter.ComidaViewHolder>() {

    private var comidas: List<String> = ArrayList()

    inner class ComidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreComidaTextView: TextView = itemView.findViewById(R.id.nombreComidaTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comida, parent, false)
        return ComidaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = comidas[position]
        holder.nombreComidaTextView.text = comida
    }

    override fun getItemCount(): Int {
        return comidas.size
    }

    fun actualizarLista(nuevaLista: List<String>) {
        comidas = nuevaLista
        notifyDataSetChanged()
    }


}
