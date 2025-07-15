package com.example.gettingstartedwithjetpackcompose.ui.theme.authentication

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    object Submit : LoginEvent
}