package com.example.gettingstartedwithjetpackcompose.ui.theme.landingPage

sealed class LandingPageEvent {
    object NotesHomeClicked      : LandingPageEvent()
    object AccountsDashboardClicked : LandingPageEvent()
}