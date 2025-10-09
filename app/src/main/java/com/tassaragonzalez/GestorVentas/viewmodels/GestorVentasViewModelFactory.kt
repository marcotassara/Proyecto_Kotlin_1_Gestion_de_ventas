package com.tassaragonzalez.GestorVentas.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GestorVentasViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GestorVentasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GestorVentasViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}