package com.example.nutrifit.db

import com.example.nutrifit.pojo.Alimento
import com.google.firebase.firestore.FirebaseFirestore

class DatabaseManager {

    private val db = FirebaseFirestore.getInstance()
    private val alimentosCollection = db.collection("alimentos")

    fun buscarAlimentos(palabraClave: String, callback: (List<Alimento>) -> Unit) {
        val resultados = mutableListOf<Alimento>()

        alimentosCollection
            .whereGreaterThanOrEqualTo("nombre", palabraClave)
            .whereLessThanOrEqualTo("nombre", palabraClave + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val nombre = document.getString("nombre")
                    val calorias = document.getLong("kcal")?.toInt() ?: 0
                    val proteinas = document.getLong("proteinas")?.toInt() ?: 0
                    val cantidad = document.getLong("cantidad")?.toInt() ?: 0
                    nombre?.let {
                        val alimento = Alimento(nombre, calorias, proteinas, cantidad)
                        resultados.add(alimento)
                    }
                }
                callback(resultados)
            }
            .addOnFailureListener { exception ->
                callback(emptyList())
            }
    }

}
