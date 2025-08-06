package com.example.gettingstartedwithjetpackcompose.data.network.authenticationNetwork

import com.example.gettingstartedwithjetpackcompose.data.network.authenticationNetwork.request.LoginRequest
import com.example.gettingstartedwithjetpackcompose.data.network.authenticationNetwork.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(

        //lets avoid headers here coz they will cause repetition

//        @Header("Authorization") authToken: String = "Bearer 50888|eOlfVR9LwpbwAxh9QTLNPaU8NZivNFXZbdcc3NSW2998c845",
//        @Header("app-key") appKey: String = "97fb852f-f24d-49d5-9375-6825bde2b3de",
//        @Header("Content-Type") contentType: String = "application/json",
//        @Header("Accept") accept: String = "application/json",
//        @Header("device-serial") deviceSerial: String = "1234567890", ///should i keep this the same?
//        @Header("device-fingerprint") deviceFingerprint: String = "184e757995907383285ca8faf5ce5f30",
        //headers return metadata about the request - this is custom

        @Body request: LoginRequest
        //turns LoginRequest into a json object
    ): Response<LoginResponse>
}


///sync/accounts
///parameter to add
//search
/// ensure one can search a user via name or phone number

//some other tags
//@GET("") Retrieving existing data from the server
//@PUT("") Updating or replacing existing data
//@DELETE("") Deleting existing data
//@PATCH("") Updating existing data partially: like changing a password
//@POST("") Adding new data