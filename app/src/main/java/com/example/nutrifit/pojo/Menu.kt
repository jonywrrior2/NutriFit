package com.example.nutrifit.pojo


data class Menu(
    var alimentos: String = "",
    var cantidad: Int = 0,
    var kcal: Double = 0.0,
    var proteinas: Double = 0.0,
    var unidad: String? = "",
    var usuario: String? = "",
    var tipo: String = ""
)


