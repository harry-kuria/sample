package com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount

sealed class MyAccountUiEvent {
    object NavigateToSettings : MyAccountUiEvent()
    object NavigateToNotifications : MyAccountUiEvent()
    object NavigateToAbout : MyAccountUiEvent()
    object NavigateToFAQ : MyAccountUiEvent()
    object NavigateToLogin : MyAccountUiEvent()
    object Back : MyAccountUiEvent()
}
