package com.example.gettingstartedwithjetpackcompose.ui.theme.register

sealed interface RegisterEvent {
    data class UsernameChanged(val value: String) : RegisterEvent
    data class EmailChanged(val value: String) : RegisterEvent
    data class PasswordChanged(val value: String) : RegisterEvent
    data class ConfirmChanged(val value: String) : RegisterEvent
    object Submit : RegisterEvent
}