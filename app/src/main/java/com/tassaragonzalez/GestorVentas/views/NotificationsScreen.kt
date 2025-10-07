package com.tassaragonzalez.GestorVentas.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.model.Notification
import com.tassaragonzalez.GestorVentas.model.NotificationType
import com.tassaragonzalez.GestorVentas.ui.components.NotificationItem

@Composable
fun NotificationsScreen() {
    // Datos de prueba que simulan lo que vendría de tu microservicio
    val notifications = listOf(
        Notification(1, "- Marco ha vendido una coca-cola", NotificationType.SUCCESS),
        Notification(2, "- Alcanzaste las ventas diarias", NotificationType.SUCCESS),
        Notification(3, "- Marco ha cerrado sesión", NotificationType.INFO),
        Notification(4, "- Aún te faltan $5000 para llegar a la meta", NotificationType.WARNING)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notifications) { notification ->
            NotificationItem(notification = notification)
        }
    }
}