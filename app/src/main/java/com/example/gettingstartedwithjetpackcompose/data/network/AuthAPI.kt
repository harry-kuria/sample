package com.example.gettingstartedwithjetpackcompose.data.network

import com.example.gettingstartedwithjetpackcompose.data.network.request.LoginRequest
import com.example.gettingstartedwithjetpackcompose.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Header("device-serial") deviceSerial: String = "1234567890",
        //headers return metadata about the request - this is custom
        @Body request: LoginRequest
        //turns LoginRequest into a json object
    ): Response<LoginResponse>
}

//some other tags
//@GET("") Retrieving existing data from the server
//@PUT("") Updating or replacing existing data
//@DELETE("") Deleting existing data
//@PATCH("") Updating existing data partially: like changing a password
//@POST("") Adding new data