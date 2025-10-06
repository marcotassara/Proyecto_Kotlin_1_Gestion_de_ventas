package com.tassaragonzalez.GestorVentas.ui.components // Asegúrate de que el package sea correcto

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun DoublePressBackToExitHandler() {
    val context = LocalContext.current
    var backPressedTime by remember { mutableStateOf(0L) }

    BackHandler(enabled = true) {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            // Si se presiona de nuevo en menos de 2 segundos, cierra la app
            (context as? Activity)?.finish()
        } else {
            // La primera vez, muestra un mensaje
            Toast.makeText(context, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}