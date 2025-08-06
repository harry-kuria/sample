package com.example.gettingstartedwithjetpackcompose.features.authentication.network.request

data class LoginRequest(
    val email: String,
    val pin: String
)