package com.tassaragonzalez.GestorVentas.model // O 'data', según el paquete que hayas creado

import androidx.annotation.DrawableRes

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    @DrawableRes val imageRes: Int? = null
)