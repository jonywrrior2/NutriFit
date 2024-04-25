package com.example.nutrifit.dbMenus

import android.util.Log
import com.example.nutrifit.pojo.Menu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

object DatabaseManagerMenu {

    private val db = FirebaseFirestore.getInstance()
    private val menusCollection = db.collection("menus")
    private val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    fun addMenu(
        menu: Menu,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("menus")
            .add(menu)
            .addOnSuccessListener { documentReference ->
                Log.d("DatabaseManagerMenu", "Menu added with ID: ${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("DatabaseManagerMenu", "Error adding menu", e)
                onFailure(e)
            }
    }

    fun getUserMenus(
        onSuccess: (List<Menu>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        currentUserEmail?.let { email ->
            menusCollection.whereEqualTo("usuario", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val menus = mutableListOf<Menu>()
                    for (document in querySnapshot.documents) {
                        // Excluir el campo "usuario" antes de mapear el documento a la clase Menu
                        val menuData = document.data?.toMutableMap()
                        menuData?.remove("usuario")
                        menuData?.remove("stability")
                        val alimentos = menuData?.get("alimento") as String
                        val cantidad = (menuData?.get("cantidad") as Long).toInt()
                        val kcal = (menuData?.get("kcal") as Long).toDouble()
                        val proteinas = (menuData?.get("proteinas") as Long).toDouble()
                        val tipo = menuData?.get("tipo") as String
                        val unidad = menuData?.get("unidad") as String
                        val fechaStr = menuData?.get("fecha") as String // Recuperar la fecha como String
                        val usuario = currentUserEmail ?: ""
                        val menu = Menu(alimentos, cantidad, kcal, proteinas, unidad, usuario, tipo, fechaStr)

                        menus.add(menu)
                    }
                    onSuccess(menus)
                }
                .addOnFailureListener { e -> onFailure(e) }
        } ?: onFailure(Exception("User not logged in"))
    }




}
