package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel

@Composable
fun SettingsScreen(viewModel: GestorVentasViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla de Ajustes")

        Button(onClick = { viewModel.navigateUp() }) {
            Text("Volver")
        }
    }
}