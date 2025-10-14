package com.tassaragonzalez.GestorVentas.data

import android.content.Context
import android.content.SharedPreferences
import com.tassaragonzalez.GestorVentas.model.Role

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    companion object {
        const val IS_LOGGED_IN = "is_logged_in"
        const val USER_ROLE = "user_role"
    }

    fun saveSession(isLoggedIn: Boolean, role: Role?) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        role?.let { editor.putString(USER_ROLE, it.name) }
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }


    fun getRole(): Role? {
        val roleName = prefs.getString(USER_ROLE, null)
        return roleName?.let { Role.valueOf(it) }
    }


    // --- FIN DEL CAMBIO ---
}