package com.example.gettingstartedwithjetpackcompose.features.authentication.uiStates

data class RegisterUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val username: String = "",
    val error: String? = null,
    val isRegistered: Boolean = false
)