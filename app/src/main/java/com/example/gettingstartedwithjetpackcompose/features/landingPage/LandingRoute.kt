package com.example.gettingstartedwithjetpackcompose.features.landingPage

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gettingstartedwithjetpackcompose.features.navigation.Routes

@ExperimentalMaterial3Api
@Composable
fun LandingRoute(navController: NavController,
                   viewModel: LandingPageViewModel = hiltViewModel()){


    val uiState by viewModel.landingPageState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.landingPageUiEvent.collect { event ->
            when (event) {
                is LandingEventUi.NavigateToNotesHome -> navController.navigate(Routes.HOME)
                is LandingEventUi.NavigateToAccountsDashboard -> navController.navigate(Routes.ACCOUNT_DASHBOARD)
            }
        }
    }

    LandingScreen(
        navController = navController,
        username = uiState.username,
        onNotesHomeClick = { viewModel.onEvent(LandingPageEvent.NotesHomeClicked) },
        onAccountsDashboardClick = { viewModel.onEvent(LandingPageEvent.AccountsDashboardClicked) }
    )
}