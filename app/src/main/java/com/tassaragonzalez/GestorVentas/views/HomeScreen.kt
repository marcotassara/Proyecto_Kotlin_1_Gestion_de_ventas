package com.tassaragonzalez.GestorVentas.views

import SalesAnalyticsCard
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.R
import com.tassaragonzalez.GestorVentas.model.Product
import com.tassaragonzalez.GestorVentas.ui.components.DoublePressBackToExitHandler
import com.tassaragonzalez.GestorVentas.ui.theme.GestorVentasTheme

// =================================================================================
// --- SECCIÓN 1: La Pantalla Principal ---
// =================================================================================
@Composable
fun HomeScreen(onLowStockClick: () -> Unit) {
    DoublePressBackToExitHandler()
    // Datos de prueba para la alerta de stock bajo
    val lowStockAlert = Product(
        id = -1L,
        name = "¡ALERTA! STOCK BAJO",
        description = "Hay productos con niveles de stock críticos.",
        price = 0.0,
        imageUrl = "",
        imageRes = R.drawable.gestor
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "REPORTE DE VENTAS CRÍTICOS",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        ProductItem(
            product = lowStockAlert,
            onClick = onLowStockClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ANALISIS DE VENTAS DIARIAS",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        SalesAnalyticsCard(percentage = 0.82f)
    }
}



@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            product.imageRes?.let { image ->
                Image(
                    painter = painterResource(id = image),
                    contentDescription = product.name,
                    modifier = Modifier.size(80.dp).padding(8.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                // Si el precio es 0.0, no lo mostramos, si no, lo formateamos
                if (product.price > 0.0) {
                    Text(text = "$${product.price}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                } else {
                    Text(text = product.description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }
            IconButton(onClick = { /* La acción principal está en la tarjeta */ }) {
                Icon(imageVector = Icons.Default.AddShoppingCart, contentDescription = "Añadir al carrito")
            }
        }
    }
}

// =================================================================================
// --- SECCIÓN 3: Previsualización ---
// =================================================================================
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    GestorVentasTheme {
        HomeScreen(onLowStockClick = {})
    }
}