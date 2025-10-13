package com.tassaragonzalez.GestorVentas.model

data class Sale(
    val id: Long,
    val clienteId: Long,
    val vendedorId: Long,
    val fecha: String,
    val total: Double,
    val detalles: List<SaleDetail>
)

data class SaleDetail(
    val id: Long,
    val productoId: Long,
    val cantidad: Int,
    val precioUnitario: Double
)