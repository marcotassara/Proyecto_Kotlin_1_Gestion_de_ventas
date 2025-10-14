package com.tassaragonzalez.GestorVentas.model.state

data class AddProductState(
    val nombre: String = "",
    val nombreError: String? = null,
    val marca: String = "",
    val marcaError: String? = null,
    val precioVenta: String = "",
    val precioVentaError: String? = null,
    val stock: String = "",
    val stockError: String? = null,
    val isActive: Boolean = false
)