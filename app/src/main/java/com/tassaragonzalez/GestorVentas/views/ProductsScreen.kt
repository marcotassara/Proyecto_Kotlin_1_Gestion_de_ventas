package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel


@Composable
fun ProductsScreen(viewModel: GestorVentasViewModel) {
    // --- CAMBIO 3: Se eliminó toda la estructura de Scaffold y TopAppBar ---
    // La pantalla ahora solo define su contenido interno.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Aquí irá la barra de búsqueda, filtros y la lista de productos.")
        // Próximamente construiremos el contenido de esta pantalla aquí.
    }
}