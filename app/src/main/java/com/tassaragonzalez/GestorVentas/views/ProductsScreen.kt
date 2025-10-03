package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.navigation.Screen
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(viewModel: GestorVentasViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos") },
                navigationIcon = {
                    IconButton(onClick = { /* Acción para abrir el menú */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                    }
                },
                actions = {
                    // Botón para volver al HomeScreen
                    IconButton(onClick = { viewModel.navigateTo(Screen.HomeScreen) }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Volver a Inicio")
                    }
                    // Botón para añadir producto

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Aquí dentro construiremos el resto de la pantalla:
            // 1. La barra de búsqueda
            // 2. Los filtros de categoría
            // 3. La lista de productos
        }
    }
}