package com.tassaragonzalez.GestorVentas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tassaragonzalez.GestorVentas.navigation.NavigationEvent
import com.tassaragonzalez.GestorVentas.navigation.Screen
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow



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
}