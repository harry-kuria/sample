package com.example.gettingstartedwithjetpackcompose.ui.theme.home

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)