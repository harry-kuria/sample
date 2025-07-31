package com.example.gettingstartedwithjetpackcompose.ui.theme.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gettingstartedwithjetpackcompose.SplashScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.notes.NotesHomeScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.screens.LoginScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.screens.RegisterScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount.MyAccountRoute
import com.example.gettingstartedwithjetpackcompose.ui.theme.notes.EditNoteScreen
import com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard.AccountsDashboardScreen

import kotlinx.coroutines.ExperimentalCoroutinesApi


object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "notes_home_screen"
    const val MY_ACCOUNT = "my_account_screen"
    const val EDIT_NOTE = "edit_note_screen"
    const val ACCOUNT_DASHBOARD = "account_dashboard"
}


@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    isLoggedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToHome = { navController.navigate(Routes.HOME) },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) },
                onNavigateToHome = { navController.navigate(Routes.HOME) }
            )
        }

//        composable(Routes.HOME) {
//            NotesHomeScreen(
//                onNavigateToMyAccount = { navController.navigate(Routes.MY_ACCOUNT) },
//                onNavigateToEditNote = { noteId -> navController.navigate("${Routes.EDIT_NOTE}/$noteId") }
//            )
//        }
        composable(Routes.HOME) {
            NotesHomeScreen(navController , viewModel = hiltViewModel())
        }

        composable(Routes.MY_ACCOUNT) {
            MyAccountRoute(navController)
        }

        composable("${Routes.EDIT_NOTE}/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            if (noteId != null) {
                EditNoteScreen(
                    noteId = noteId,
                    onNavigateBack = { navController.navigate(Routes.HOME) },
                )
            } else {
                Text("Invalid note ID")
            }
        }

        composable(Routes.ACCOUNT_DASHBOARD){
            AccountsDashboardScreen(
                viewModel = hiltViewModel(),
                navController
            )
        }
    }
}
