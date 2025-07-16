package com.example.gettingstartedwithjetpackcompose.ui.theme.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gettingstartedwithjetpackcompose.ui.theme.home.HomeScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.screens.LoginScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.screens.RegisterScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount.MyAccountRoute


object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home screen"
    const val MY_ACCOUNT = "my account screen"
}

@ExperimentalMaterial3Api
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), isLoggedIn: Boolean) {
    //val context = LocalContext.current
    //val userDao: UserDao = remember { AppDatabase.getDatabase(context).UserDao() }

    NavHost(
        navController  = navController,
        startDestination = if (isLoggedIn) {Routes.HOME} else {Routes.LOGIN}
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
            HomeScreen(
                onNavigateToMyAccount = { navController.navigate(Routes.MY_ACCOUNT) },
            )
        }

        composable(Routes.MY_ACCOUNT) {
            MyAccountRoute(navController)
        }
    }
}