package com.tassaragonzalez.GestorVentas.model

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val role: Role,
    val isActive: Boolean //

)