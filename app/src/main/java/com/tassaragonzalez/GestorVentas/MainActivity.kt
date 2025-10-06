package com.tassaragonzalez.GestorVentas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tassaragonzalez.GestorVentas.navigation.NavigationEvent
import com.tassaragonzalez.GestorVentas.navigation.Screen
import com.tassaragonzalez.GestorVentas.ui.theme.GestorVentasTheme
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel
import com.tassaragonzalez.GestorVentas.views.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorVentasTheme {
                val viewModel: GestorVentasViewModel = viewModel()
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Escuchador de eventos
                LaunchedEffect(Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    event.popUpToRoute?.let { popUpTo(it.route) { inclusive = event.inclusive } }
                                    launchSingleTop = event.singleTop
                                }
                            }
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                        }
                    }
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(
                            viewModel = viewModel,
                            drawerState = drawerState,
                            scope = scope
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            // Solo muestra la barra si no estamos en Login o Register
                            if (currentRoute != Screen.LoginScreen.route && currentRoute != Screen.RegisterScreen.route) {
                                TopAppBar(
                                    title = {
                                        // Título dinámico
                                        when (currentRoute) {
                                            Screen.AddProductScreen.route -> Text("Agregar Producto")
                                            Screen.HomeScreen.route -> Text("¡Bienvenido a Neg!")
                                            Screen.ProductsScreen.route -> Text("Productos")
                                            Screen.SettingsScreen.route -> Text("Configuraciones")
                                            Screen.RegisterClientScreen.route -> Text("Registrar Cliente")
                                            else -> Text("Gestor de Ventas")
                                        }
                                    },
                                    navigationIcon = {
                                        // --- LÓGICA CORREGIDA ---
                                        // Definimos las pantallas que son "principales" y llevan menú
                                        val isMainScreen = currentRoute == Screen.HomeScreen.route ||
                                                currentRoute == Screen.ProductsScreen.route

                                        if (isMainScreen) {
                                            // Si es una pantalla principal, muestra el Menú
                                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                                            }
                                        } else {
                                            // Para CUALQUIER OTRA pantalla (AddProduct, Settings, etc.), muestra "Volver"
                                            IconButton(onClick = { viewModel.navigateUp() }) {
                                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                                            }
                                        }
                                    },
                                    actions = {
                                        // Acciones dinámicas (esto ya lo tenías bien)
                                        if (currentRoute == Screen.ProductsScreen.route) {
                                            IconButton(onClick = { viewModel.navigateTo(Screen.HomeScreen) }) {
                                                Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio")
                                            }
                                            IconButton(onClick = { viewModel.navigateTo(Screen.AddProductScreen) }) {
                                                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir")
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.LoginScreen.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.LoginScreen.route) { LoginScreen(viewModel = viewModel) }
                            composable(Screen.RegisterScreen.route) { RegisterScreen(viewModel = viewModel) }
                            composable(Screen.HomeScreen.route) { HomeScreen(onLowStockClick = { viewModel.navigateTo(Screen.ProductsScreen) }) }
                            composable(Screen.ProductsScreen.route) { ProductsScreen(viewModel = viewModel) }
                            composable(Screen.SettingsScreen.route) { SettingsScreen(viewModel = viewModel) }
                            composable(Screen.RegisterClientScreen.route) { RegisterClientScreen(viewModel = viewModel) }
                            composable(Screen.AddProductScreen.route) { AddProductScreen(viewModel = viewModel) }
                        }
                    }
                }
            }
        }
    }
}

// Ponemos la función DrawerContent aquí para que MainActivity la encuentre
@Composable
fun DrawerContent(
    viewModel: GestorVentasViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    ModalDrawerSheet {
        Text("Menú Principal", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        Divider()
        NavigationDrawerItem(
            label = { Text(text = "Registrar Nuevo Cliente") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.RegisterClientScreen)
            }
        )
        NavigationDrawerItem(
            label = { Text(text = "Productos") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.ProductsScreen)
            }
        )
        NavigationDrawerItem(
            label = { Text(text = "Configuraciones") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.SettingsScreen)
            }
        )
    }
}