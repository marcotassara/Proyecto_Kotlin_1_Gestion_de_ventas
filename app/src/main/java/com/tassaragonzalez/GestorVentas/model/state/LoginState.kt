package com.tassaragonzalez.GestorVentas.model.state

data class LoginState (
    val username: String = "",
    val password: String = "",
    val rememberSession: Boolean = false,
    val error: String? = null

)
