package com.example.cuidadodeplantas


data class Planta(
    val nombre: String,
    val riego: String,
    val tipo: String = "Sin especificar",
    val luz: String = "Sin especificar"
)

