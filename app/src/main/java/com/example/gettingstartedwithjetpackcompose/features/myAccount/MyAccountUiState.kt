package com.example.gettingstartedwithjetpackcompose.features.myAccount

data class MyAccountUiState(
    val username: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)