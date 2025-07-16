package com.example.gettingstartedwithjetpackcompose.ui.theme.uiStates

data class LoginUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)

data class RegisterUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val username: String = "",
    val error: String? = null,
    val isRegistered: Boolean = false
)

//data class HomeUiState(
//    val username: String = "",
//    val email: String = ""
//)

data class MyAccountUiState(
    val username: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)