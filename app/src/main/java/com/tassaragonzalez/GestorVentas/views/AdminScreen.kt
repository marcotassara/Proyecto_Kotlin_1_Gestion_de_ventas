package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.navigation.Screen
import com.tassaragonzalez.GestorVentas.ui.theme.GestorVentasTheme
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel

@Composable
fun AdminScreen(viewModel: GestorVentasViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Panel de Administrador",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Fila de tarjetas para las acciones principales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AdminActionCard(
                modifier = Modifier.weight(1f),
                title = "Agregar Producto",
                icon = Icons.Default.Add,
                onClick = { viewModel.navigateTo(Screen.AddProductScreen) }
            )
            AdminActionCard(
                modifier = Modifier.weight(1f),
                title = "Notificaciones",
                icon = Icons.Default.Notifications,
                onClick = { viewModel.navigateTo(Screen.NotificationsScreen) }
            )
        }

        // Aquí podrías añadir más tarjetas o resúmenes para el admin
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminActionCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(120.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminScreenPreview() {
    GestorVentasTheme {

    }
}