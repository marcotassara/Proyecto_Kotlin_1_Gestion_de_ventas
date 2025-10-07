package com.tassaragonzalez.GestorVentas.model

// Un 'enum' para clasificar los tipos de notificación y asignarles un color
enum class NotificationType {
    SUCCESS, // Verde
    WARNING, // Amarillo
    INFO,    // Azul/Normal
    ERROR    // Rojo
}

data class Notification(
    val id: Long,
    val message: String,
    val type: NotificationType
)