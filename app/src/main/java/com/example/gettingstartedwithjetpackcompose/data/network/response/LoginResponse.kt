package com.example.gettingstartedwithjetpackcompose.data.network.response

data class LoginResponse(
    val token: String,
    //JWT token. Like a temporary access key. Helps the server authenticate the user is so they don't have to keep track of the username and password all the time
    val userId: String,
    //more permanent
    val name: String)