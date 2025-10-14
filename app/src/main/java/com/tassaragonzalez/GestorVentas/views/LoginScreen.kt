package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

            modifier = Modifier.fillMaxWidth(),
            isError = state.error != null
        )
        Text("Usuarios de prueba: vendedor, admin ")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onLoginFieldChange("password", it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.error != null,
            visualTransformation = PasswordVisualTransformation()
        )
        Text("Contraseña: 123456")
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.rememberSession,
                onCheckedChange = { viewModel.onRememberSessionChange(it) }
            )
            Text("Mantener la sesión abierta")
        }


        // Muestra el mensaje de error si existe
        val error = state.error
        if (error != null) {
            Text(
                text = error, // Usamos la copia local segura
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onLoginClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
        TextButton(onClick = { viewModel.onRegisterClick() }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}