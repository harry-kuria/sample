package com.example.gettingstartedwithjetpackcompose.ui.theme.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gettingstartedwithjetpackcompose.ui.theme.HomeScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.login.LoginScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.register.RegisterScreen


object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home screen"
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()){
    //val context = LocalContext.current
    //val userDao: UserDao = remember { AppDatabase.getDatabase(context).UserDao() }

    NavHost(
        navController  = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                //userDao = userDao,
                onNavigateToHome = {navController.navigate(Routes.HOME)},
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                //userDao = userDao,
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) },
                onNavigateToHome = { navController.navigate(Routes.HOME) }
            )
        }

        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}