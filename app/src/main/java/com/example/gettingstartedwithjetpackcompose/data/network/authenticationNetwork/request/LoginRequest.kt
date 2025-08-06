package com.example.gettingstartedwithjetpackcompose.data.network.authenticationNetwork.request

data class LoginRequest(
    val email: String,
    val pin: String
)