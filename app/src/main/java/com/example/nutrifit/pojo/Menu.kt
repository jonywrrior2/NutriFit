package com.example.nutrifit.pojo


import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.time.LocalDate

data class Menu(
    val alimentos: String,
    val cantidad: Int,
    val kcal: Double,
    val proteinas: Double,
    val unidad: String,
    val usuario: String,
    val tipo: String,
    val fecha:  String
) : Serializable