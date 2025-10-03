package com.tassaragonzalez.GestorVentas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tassaragonzalez.GestorVentas.navigation.NavigationEvent
import com.tassaragonzalez.GestorVentas.navigation.Screen
import com.tassaragonzalez.GestorVentas.ui.theme.GestorVentasTheme
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel
import com.tassaragonzalez.GestorVentas.views.HomeScreen
import com.tassaragonzalez.GestorVentas.views.LoginScreen
import com.tassaragonzalez.GestorVentas.views.ProductsScreen
import com.tassaragonzalez.GestorVentas.views.RegisterClientScreen
import com.tassaragonzalez.GestorVentas.views.RegisterScreen
import com.tassaragonzalez.GestorVentas.views.SettingsScreen
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorVentasTheme {
                val viewModel: GestorVentasViewModel = viewModel()
                val navController = rememberNavController()

                // Escuchador de eventos de navegación
                LaunchedEffect(Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    // Aquí se aplicarían lógicas como singleTop, popUpTo, etc.
                                }
                            }
                            is NavigationEvent.NavigateUp -> {
                                navController.navigateUp()
                            }

                            is NavigationEvent.PopBackStack -> {
                                navController.popBackStack()
                            }
                        }
                    }
                }

                // Contenedor principal con el NavHost
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable(Screen.LoginScreen.route) {
                            LoginScreen(viewModel = viewModel)
                        }

                        composable(Screen.RegisterScreen.route) {
                            RegisterScreen(viewModel = viewModel)
                        }

                        composable(Screen.HomeScreen.route) {
                            HomeScreen(viewModel = viewModel)
                        }

                        composable(Screen.RegisterClientScreen.route) {
                            RegisterClientScreen(viewModel = viewModel)
                        }

                        composable(Screen.SettingsScreen.route) {
                            SettingsScreen(viewModel = viewModel)
                        }

                        composable(Screen.ProductsScreen.route) {
                            ProductsScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}