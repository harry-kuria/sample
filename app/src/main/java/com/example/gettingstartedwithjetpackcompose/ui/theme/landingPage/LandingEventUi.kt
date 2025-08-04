package com.example.gettingstartedwithjetpackcompose.ui.theme.landingPage

sealed class LandingEventUi {
    object NavigateToNotesHome : LandingEventUi()
    object NavigateToAccountsDashboard : LandingEventUi()
}