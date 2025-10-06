package com.tassaragonzalez.GestorVentas.navigation

sealed class Screen(val route: String) {

    data object LoginScreen : Screen("login_screen")
    data object RegisterScreen : Screen("register_screen")
    data object HomeScreen :Screen("home_screen")
    data object RegisterClientScreen : Screen("register_client_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object ProductsScreen : Screen("products_screen")

    data object AddProductScreen : Screen("add_product_screen")


    data class ProductDetailScreen(val productId: Long) : Screen("product_detail/{productId}") {
        fun buildRoute(): String {
            return "product_detail/$productId"
        }
    }
}