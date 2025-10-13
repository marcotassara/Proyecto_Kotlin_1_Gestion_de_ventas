package com.tassaragonzalez.GestorVentas.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tassaragonzalez.GestorVentas.R
import com.tassaragonzalez.GestorVentas.data.SessionManager
import com.tassaragonzalez.GestorVentas.model.Product
import com.tassaragonzalez.GestorVentas.model.Role
import com.tassaragonzalez.GestorVentas.model.User
import com.tassaragonzalez.GestorVentas.model.state.LoginState
import com.tassaragonzalez.GestorVentas.model.state.RegisterState
import com.tassaragonzalez.GestorVentas.navigation.NavigationEvent
import com.tassaragonzalez.GestorVentas.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GestorVentasViewModel(context: Context) : ViewModel() {
    private val sessionManager = SessionManager(context)

    // --- USUARIOS DE PRUEBA ---
    private val simulatedUsers = listOf(
        User(1, "vendedor", "123456", Role.VENDEDOR, true),
        User(2, "admin", "123456", Role.ADMIN, true),
        User(3, "inactivo", "123456", Role.VENDEDOR, false)
    )

    // En GestorVentasViewModel.kt

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    // --- ESTADOS DE LA UI ---
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts = _filteredProducts.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // --- EVENTOS DE NAVEGACIÓN ---
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        loadProducts()
    }

    // --- LÓGICA DE LOGIN ---
    fun onLoginFieldChange(field: String, value: String) {
        _loginState.value = when (field) {
            "username" -> _loginState.value.copy(username = value, error = null) // Limpia el error al escribir
            "password" -> _loginState.value.copy(password = value, error = null) // Limpia el error al escribir
            else -> _loginState.value
        }
    }

    fun onRememberSessionChange(isChecked: Boolean) {
        _loginState.value = _loginState.value.copy(rememberSession = isChecked)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val currentState = _loginState.value
            val user = simulatedUsers.find { it.username == currentState.username && it.password == currentState.password }

            if (user == null) {
                _loginState.value = currentState.copy(error = "Credenciales incorrectas")
                return@launch
            }

            if (!user.isActive) {
                _loginState.value = currentState.copy(error = "El usuario está inactivo")
                return@launch
            }

            _currentUser.value = user

            if (currentState.rememberSession) {
                sessionManager.saveSession(true)
            }

            val destination = if (user.role == Role.ADMIN) Screen.AdminScreen else Screen.HomeScreen

            _navigationEvents.emit(
                NavigationEvent.NavigateTo(
                    route = destination,
                    popUpToRoute = Screen.LoginScreen,
                    inclusive = true
                )
            )
        }
    }

    // --- LÓGICA DE REGISTRO ---
    fun onRegisterFieldChange(field: String, value: String) {
        _registerState.value = when (field) {
            "name" -> _registerState.value.copy(name = value, nameError = null)
            "age" -> _registerState.value.copy(age = value, ageError = null)
            "email" -> _registerState.value.copy(email = value, emailError = null)
            "password" -> _registerState.value.copy(password = value, passwordError = null)
            "confirmPassword" -> _registerState.value.copy(confirmPassword = value, confirmPasswordError = null)
            else -> _registerState.value
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onRegisterSubmit() {
        val currentState = _registerState.value
        val nameError = if (currentState.name.isBlank()) "El nombre no puede estar vacío" else null
        val ageError = if (currentState.age.isBlank()) "La edad no puede estar vacía" else null
        val emailError = if (!isValidEmail(currentState.email)) "El formato del email no es válido" else null
        val passwordError = if (currentState.password.length < 6) "La contraseña debe tener al menos 6 caracteres" else null
        val confirmPasswordError = if (currentState.password != currentState.confirmPassword) "Las contraseñas no coinciden" else null
        val hasError = listOf(nameError, ageError, emailError, passwordError, confirmPasswordError).any { it != null }

        _registerState.value = currentState.copy(
            nameError = nameError,
            ageError = ageError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )

        if (!hasError) {
            println("¡Registro Válido!: $currentState")
            navigateUp()
        }
    }

    // --- LÓGICA DE PRODUCTOS ---
    private fun loadProducts() {
        val testProducts = listOf(
            Product(1, "Coca-Cola 500ml", "Bebida gaseosa", 750.0, "", R.drawable.gestor),
            Product(2, "Arroz Integral 1kg", "Arroz orgánico", 1500.0, "", R.drawable.gestor),
            Product(3, "Detergente Líquido", "Para ropa de color", 2500.0, "", R.drawable.gestor),
            Product(4, "Papas Fritas", "Snack salado", 650.0, "", R.drawable.gestor)
        )
        _allProducts.value = testProducts
        _filteredProducts.value = testProducts
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _filteredProducts.value = if (query.isBlank()) {
            _allProducts.value
        } else {
            _allProducts.value.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
    }

    // --- LÓGICA GENERAL DE NAVEGACIÓN ---
    fun navigateTo(screen: Screen, popUpToRoute: Screen? = null, inclusive: Boolean = false, singleTop: Boolean = false) {
        viewModelScope.launch {
            _navigationEvents.emit(
                NavigationEvent.NavigateTo(
                    route = screen,
                    popUpToRoute = popUpToRoute,
                    inclusive = inclusive,
                    singleTop = singleTop
                )
            )
        }
    }

    fun navigateUp() { viewModelScope.launch { _navigationEvents.emit(NavigationEvent.NavigateUp) } }
    fun onRegisterClick() { viewModelScope.launch { _navigationEvents.emit(NavigationEvent.NavigateTo(Screen.RegisterScreen)) } }
    fun onAddProductClick() { viewModelScope.launch { _navigationEvents.emit(NavigationEvent.NavigateTo(Screen.AddProductScreen)) } }

    fun onLogoutClick() {
        sessionManager.saveSession(false)
        _currentUser.value = null
        viewModelScope.launch {
            _navigationEvents.emit(
                NavigationEvent.NavigateTo(
                    route = Screen.LoginScreen,
                    popUpToRoute = Screen.HomeScreen,
                    inclusive = true
                )
            )
        }
    }

    fun notifyAdminOfLowStock() {
        // En el futuro, esta función llamará al microservicio de notificaciones.
        // Por ahora, simulamos la acción con un mensaje.
        println("NOTIFICACIÓN: ¡El vendedor ha reportado stock bajo!")
        // Opcional: Podríamos emitir un evento para mostrar un Toast de confirmación en la UI.
    }
}