package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel

@Composable
fun LoginScreen(viewModel: GestorVentasViewModel) {
    val state by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login / Register", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.onLoginFieldChange("username", it) },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onLoginFieldChange("password", it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = state.rememberSession,
                onCheckedChange = {viewModel.onRememberSessionChange(it)}

            )
            Text("Mantener la sesión abierta")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.onLoginClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }


        TextButton(onClick = { viewModel.onRegisterClick() }) { // Llama a una nueva función
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}