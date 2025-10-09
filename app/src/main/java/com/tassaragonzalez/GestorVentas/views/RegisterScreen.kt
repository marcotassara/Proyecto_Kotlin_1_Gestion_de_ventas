package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
fun RegisterScreen(viewModel: GestorVentasViewModel) {
    val state by viewModel.registerState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registro de Vendedor", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onRegisterFieldChange("name", it) },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.nameError != null,  // parametros para el error, datos temporales
            supportingText = {
                val error = state.nameError
                if (error != null) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = state.age,
            onValueChange = { viewModel.onRegisterFieldChange("age", it) },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.ageError != null,
            supportingText = {
                val error = state.ageError
                if (error != null) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(


            value = state.email,
            onValueChange = { viewModel.onRegisterFieldChange("email", it) },
            label = { Text("Gmail") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.emailError != null,
            supportingText = {
                val error = state.emailError
                if (error != null) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }

        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onRegisterFieldChange("password", it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.passwordError != null,
            supportingText = {
                val error = state.passwordError
                if (error != null) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = { viewModel.onRegisterFieldChange("confirmPassword", it) },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.confirmPasswordError != null,
            supportingText = {
                val error = state.confirmPasswordError
                if (error != null) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.onRegisterSubmit() }, modifier = Modifier.fillMaxWidth()) {
            Text("OK")
        }
        TextButton(onClick = { viewModel.navigateUp() }) {
            Text("Ya tengo una cuenta")
        }
    }
}