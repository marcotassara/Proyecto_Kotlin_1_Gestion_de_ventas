package com.tassaragonzalez.GestorVentas.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.model.Notification
import com.tassaragonzalez.GestorVentas.model.NotificationType

@Composable
fun NotificationItem(notification: Notification) {
    // Asigna un color a la tarjeta según el tipo de notificación
    val cardColor = when (notification.type) {
        NotificationType.SUCCESS -> Color(0xFFC8E6C9) // Verde pastel
        NotificationType.WARNING -> Color(0xFFFFF9C4) // Amarillo pastel
        NotificationType.INFO -> MaterialTheme.colorScheme.surfaceVariant
        NotificationType.ERROR -> Color(0xFFFFCDD2) // Rojo pastel
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Text(
            text = notification.message,
            modifier = Modifier.padding(16.dp)
        )
    }
}