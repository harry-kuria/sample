package com.example.gettingstartedwithjetpackcompose.data.network.authenticationNetwork.response

data class LoginResponse(
    //val token: String,
    //JWT token. Like a temporary access key. Helps the server authenticate the user is so they don't have to keep track of the username and password all the time
    val status: String,
    //more permanent
    val message: String,
    val data : UserDto
)


data class UserDto(
    val id: Long,
    val username: String,
    val email: String
)
