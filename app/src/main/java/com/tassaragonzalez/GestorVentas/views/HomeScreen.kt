package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable // <-- Esta anotación es la que lo convierte en un componente visual
fun HomeScreen() { // <-- Fíjate que es una 'fun' (función), no una 'class'
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Gestor de Ventas") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "¡BIENVENIDO!")
            Button(onClick = { /* acción futura */ }) {
                Text("Presióname")
            }
            Image(
                painter = painterResource(id = R.drawable.gestor),
                contentDescription = "Logo App"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}