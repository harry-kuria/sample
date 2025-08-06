package com.example.gettingstartedwithjetpackcompose.features.landingPage

sealed class LandingEventUi {
    object NavigateToNotesHome : LandingEventUi()
    object NavigateToAccountsDashboard : LandingEventUi()
}