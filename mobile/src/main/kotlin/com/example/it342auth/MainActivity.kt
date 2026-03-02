package com.example.it342auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.it342auth.ui.screens.LoginScreen
import com.example.it342auth.ui.screens.RegisterScreen
import com.example.it342auth.ui.screens.DashboardScreen
import com.example.it342auth.ui.theme.IT342AuthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IT342AuthTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("register") {
                        RegisterScreen(navController = navController)
                    }
                    composable("login") {
                        LoginScreen(navController = navController)
                    }
                    composable("dashboard?token={token}") { backStackEntry ->
                        val token = backStackEntry.arguments?.getString("token") ?: ""
                        DashboardScreen(token = token, navController = navController)
                    }
                }
            }
        }
    }
}
