package com.tassaragonzalez.GestorVentas.viewmodels

import android.content.Context

import androidx.lifecycle.viewModelScope
import com.tassaragonzalez.GestorVentas.data.SessionManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tassaragonzalez.GestorVentas.model.Product
import com.tassaragonzalez.GestorVentas.model.state.LoginState
import com.tassaragonzalez.GestorVentas.model.state.RegisterState
import com.tassaragonzalez.GestorVentas.navigation.NavigationEvent
import com.tassaragonzalez.GestorVentas.navigation.Screen
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class GestorVentasViewModel(context: Context) : ViewModel() {

    private val sessionManager = SessionManager(context)

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(screen))
        }
    }



    fun navigateUp() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateUp)
        }
    }

    fun onLoginClick() {
        val currentState = _loginState.value
        // En el futuro, aquí validarías 'currentState.username' y 'currentState.password' con el backend.

        if (currentState.rememberSession) {
            sessionManager.saveSession(true) // Guarda la sesión si el checkbox está marcado
        }

        viewModelScope.launch {
            _navigationEvents.emit(
                NavigationEvent.NavigateTo(
                    route = Screen.HomeScreen,
                    popUpToRoute = Screen.LoginScreen,
                    inclusive = true
                )
            )
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(Screen.RegisterScreen))
        }
    }

    fun onAddProductClick() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(Screen.AddProductScreen))
        }
    }  // AUN NO SE USA ESTE BTN COMPAÑERO

    // Dentro de la clase GestorVentasViewModel
    fun onLogoutClick() {
        viewModelScope.launch {
            _navigationEvents.emit(
                NavigationEvent.NavigateTo(
                    route = Screen.LoginScreen,
                    popUpToRoute = Screen.HomeScreen, // Limpia el historial HASTA HomeScreen
                    inclusive = true // Le decimos que TAMBIÉN borre HomeScreen del historial
                )
            )
        }
    }

    // 1. La lista "maestra" y privada de todos los productos.
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())

    // 2. El texto de búsqueda actual. La UI lo leerá.
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // 3. La lista FILTRADA de productos. Esta es la que la UI mostrará.
    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts = _filteredProducts.asStateFlow()

    init {
        // Al iniciar el ViewModel, cargamos los productos de prueba.
        loadProducts()
    }
   // Instanciamos el _registerStates; con private porque es el unico lugar donde va a estar en el gestor viewel
    // el MVVM.
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()


    private fun loadProducts() {
        // En el futuro, aquí harás la llamada a Retrofit para traer los productos reales.
        val testProducts = listOf(
            Product(1, "Coca-Cola 500ml", "Bebida gaseosa", 750.0, ""),
            Product(2, "Arroz Integral 1kg", "Arroz orgánico", 1500.0, ""),
            Product(3, "Detergente Líquido", "Para ropa de color", 2500.0, ""),
            Product(4, "Papas Fritas", "Snack salado", 650.0, "")
        )
        _allProducts.value = testProducts
        _filteredProducts.value = testProducts // Al inicio, la lista filtrada es la lista completa.
    }

    // La función que la UI llamará cada vez que el usuario escriba.
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // Filtramos la lista maestra y actualizamos la lista filtrada.
        _filteredProducts.value = if (query.isBlank()) {
            _allProducts.value // Si la búsqueda está vacía, muestra todos los productos
        } else {
            _allProducts.value.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
    }

    // Creamos una funcion donde llamaremos a mi registro de estados anashei:
    fun onRegisterFieldChange(field: String, value: String) {
        _registerState.value = when (field) {
            "name" -> _registerState.value.copy(name = value)
            "age" -> _registerState.value.copy(age = value)
            "email" -> _registerState.value.copy(email = value)
            "password" -> _registerState.value.copy(password = value)
            "confirmPassword" -> _registerState.value.copy(confirmPassword = value)
            else -> _registerState.value
        }
    }

    // En GestorVentasViewModel.kt

    // Esta es una función privada de ayuda para validar el email.
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

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun onLoginFieldChange(field: String, value: String) {
        _loginState.value = when (field) {
            "username" -> _loginState.value.copy(username = value)
            "password" -> _loginState.value.copy(password = value)
            else -> _loginState.value
        }
    }

    fun onRememberSessionChange(isChecked: Boolean) {
        _loginState.value = _loginState.value.copy(rememberSession = isChecked)
    }
}  // cierre de clase (class GestorVentasViewModel )


