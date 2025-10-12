package com.tassaragonzalez.GestorVentas.views

import SalesAnalyticsCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.ui.theme.GestorVentasTheme

// Modelo de datos de prueba para el resumen de ventas
data class SaleSummary(val name: String, val quantity: Int, val profit: Int)

@Composable
fun AnalyticsScreen() {
   // Reutilización de la tabla del salesPercentage
    val salesPercentage = 0.82f

    // Datos de prueba
    val saleSummaries = listOf(
        SaleSummary("Coca-cola", 2, 1500),
        SaleSummary("audifonos", 2, 4000),
        SaleSummary("manzanas", 2, 1800),
        SaleSummary("Relojes", 2, 5000)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SalesAnalyticsCard(percentage = salesPercentage)

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Resumen de ventas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Nombre", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
            Text("Ventas", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Ganancia", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        }
        Divider(modifier = Modifier.padding(vertical = 4.dp))


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(saleSummaries) { summary ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(summary.name, modifier = Modifier.weight(2f))
                    Text(summary.quantity.toString(), modifier = Modifier.weight(1f))
                    Text("$${summary.profit}", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnalyticsScreenPreview() {
    GestorVentasTheme {
        AnalyticsScreen()
    }
}