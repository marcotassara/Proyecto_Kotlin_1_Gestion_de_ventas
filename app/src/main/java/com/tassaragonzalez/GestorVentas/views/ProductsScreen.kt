package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.ui.components.DoublePressBackToExitHandler
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(viewModel: GestorVentasViewModel) {
    DoublePressBackToExitHandler()
    // "Escuchamos" los estados del ViewModel
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredProducts by viewModel.filteredProducts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- BARRA DE BÚSQUEDA ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChanged,
            label = { Text("Buscar producto...") },

            // 👇 --- ESTA ES LA LÍNEA QUE FALTABA --- 👇
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Icono de Búsqueda") },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- FILTROS DE CATEGORÍA ---
        val categories = listOf("Todos", "Bebestibles", "Comida")
        var selectedCategory by remember { mutableStateOf(categories.first()) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = (category == selectedCategory),
                    onClick = { selectedCategory = category },
                    label = { Text(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- LISTA DE PRODUCTOS ---
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredProducts) { product ->
                ProductItem(
                    product = product,
                    onClick = { /* Acción futura para ver el detalle del producto */ }
                )
            }
        }
    }
}