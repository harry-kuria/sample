package com.example.gettingstartedwithjetpackcompose.ui.theme.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.ExperimentalCoroutinesApi


object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "notes_home_screen"
    const val MY_ACCOUNT = "my_account_screen"
    const val EDIT_NOTE = "edit_note_screen"
}

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), isLoggedIn: Boolean) {
    //val context = LocalContext.current
    //val userDao: UserDao = remember { AppDatabase.getDatabase(context).UserDao() }

    NavHost(
        navController  = navController,
        startDestination = Routes.SPLASH //if (isLoggedIn) {Routes.HOME} else {Routes.LOGIN}
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

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
            NotesHomeScreen(
                onNavigateToMyAccount = { navController.navigate(Routes.MY_ACCOUNT) },
                onNavigateToEditNote = { noteId -> navController.navigate("${Routes.EDIT_NOTE}/$noteId") }
                //allows user to navigate to a specific note based on id
            )
        }

        composable(Routes.MY_ACCOUNT) {
            MyAccountRoute(navController)
        }

        composable("${Routes.EDIT_NOTE}/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            if (noteId != null) {
                EditNoteScreen(
                    noteId = noteId,
                    onNavigateBack = { navController.popBackStack() }
                )
            } else {
                Text("Invalid note ID")
            }
        }
    }
}