package com.tassaragonzalez.GestorVentas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tassaragonzalez.GestorVentas.model.Product

import com.tassaragonzalez.GestorVentas.navigation.NavigationEvent
import com.tassaragonzalez.GestorVentas.navigation.Screen
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class GestorVentasViewModel : ViewModel() {
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
        viewModelScope.launch {
            // Ordenamos navegar al HomeScreen, pero con una regla especial:
            // 'popUpTo' borra el historial de navegación HASTA el LoginScreen.
            // 'inclusive = true' asegura que el LoginScreen también se borre del historial.
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
    }

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

    // 4. La función que la UI llamará cada vez que el usuario escriba.
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
}