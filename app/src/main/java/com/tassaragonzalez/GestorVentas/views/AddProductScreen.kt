package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(viewModel: GestorVentasViewModel) {
    val state by viewModel.addProductState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Producto") },
                

            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = state.nombre,
                onValueChange = { viewModel.onAddProductFieldChange("nombre", it)},
                label = { Text("Nombre") },
                isError = state.nombreError != null,
                supportingText = {
                    state.nombreError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.marca,
                onValueChange = { viewModel.onAddProductFieldChange("marca", it)  },
                label = { Text("Marca") },
                isError = state.marcaError != null,
                supportingText = {
                    state.marcaError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.precioVenta,
                onValueChange = { viewModel.onAddProductFieldChange("precio venta", it ) },
                label = { Text("Precio Venta") },
                isError = state.precioVentaError != null,
                supportingText = {
                    state.precioVentaError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.stock,
                onValueChange = { viewModel.onAddProductFieldChange("stock", it)  },
                label = { Text("Stock") },
                isError = state.stockError != null,
                supportingText = {
                    state.stockError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Acción (Activo/Inactivo)")
                Switch(checked = state.isActive, onCheckedChange = { viewModel.onAddProductActiveChange(it) })
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { viewModel.onAddProductSubmit()},
                modifier = Modifier.fillMaxWidth()) {
                Text("Guardar Producto")
            }
        }
    }
}