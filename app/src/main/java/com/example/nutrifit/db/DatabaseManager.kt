package com.example.nutrifit.db

import com.google.firebase.firestore.FirebaseFirestore

class DatabaseManager {

    private val db = FirebaseFirestore.getInstance()
    private val alimentosCollection = db.collection("alimentos")

    fun buscarAlimentos(palabraClave: String, callback: (List<String>) -> Unit) {
        val resultados = mutableListOf<String>()

        alimentosCollection
            .whereGreaterThanOrEqualTo("nombre", palabraClave)
            .whereLessThanOrEqualTo("nombre", palabraClave + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val nombre = document.getString("nombre")
                    nombre?.let { resultados.add(it) }
                }
                callback(resultados)
            }
            .addOnFailureListener { exception ->
                callback(emptyList())
            }
    }
}
