package com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.gettingstartedwithjetpackcompose.ui.theme.nav.Routes
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MyAccountRoute(navController: NavController,
                    viewModel: MyAccountViewModel = hiltViewModel()){


    val uiState = viewModel.myAccountState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.myAccountUiEvent.collect { event ->
            when (event) {
                is MyAccountUiEvent.NavigateToSettings -> navController.navigate("settings")
                is MyAccountUiEvent.NavigateToNotifications -> navController.navigate("notifications")
                is MyAccountUiEvent.NavigateToFAQ -> navController.navigate("faq")
                is MyAccountUiEvent.NavigateToAbout -> navController.navigate("about")
                is MyAccountUiEvent.NavigateToLogin -> {navController.navigate(Routes.LOGIN) {
                        popUpTo(0)
                    }
                }
                is MyAccountUiEvent.Back -> navController.popBackStack()
            }
        }
    }

    MyAccountScreen(
        username = uiState.username,
        email = uiState.email,
        onSettingsClick = { viewModel.onEvent(MyAccountEvent.SettingsClicked) },
        onNotificationsClick = { viewModel.onEvent(MyAccountEvent.NotificationsClicked) },
        onFAQClick = { viewModel.onEvent(MyAccountEvent.FAQClicked) },
        onAboutClick = { viewModel.onEvent(MyAccountEvent.AboutClicked) },
        onLogoutClick = { viewModel.onEvent(MyAccountEvent.LogoutClicked) },
        onBackClick = { viewModel.onEvent(MyAccountEvent.BackClicked) }
    )
}