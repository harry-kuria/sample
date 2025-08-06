package com.example.gettingstartedwithjetpackcompose.features.landingPage

sealed class LandingPageEvent {
    object NotesHomeClicked: LandingPageEvent()
    object AccountsDashboardClicked : LandingPageEvent()
}