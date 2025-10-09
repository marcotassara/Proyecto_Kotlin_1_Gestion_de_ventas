package com.tassaragonzalez.GestorVentas.model.state

data class RegisterState (
    val name: String = "",
    val nameError: String? = null,

    val age: String = "",
    val ageError: String? = null,

    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null,

    val confirmPassword: String = "",
    val confirmPasswordError: String? = null

)