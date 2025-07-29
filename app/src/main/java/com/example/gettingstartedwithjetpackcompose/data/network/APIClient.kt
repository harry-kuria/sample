package com.example.gettingstartedwithjetpackcompose.data.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    //used to make all the requests
    //configures the client once. holds the interceptors, baseUrls, json converters etc

    //debugging in logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        //BODY returns everything (recommended for debugging)
        //others include NONE(returns nothing), BASIC(request and response only), HEADERS(BASIC + headers)
    }

    //private val chuckerInterceptor = ChuckerInterceptor.Builder(/*context = */)

    private val client = OkHttpClient.Builder()
        //app needs OkHTTPClient to connect to the server as it acts as a handler
        //making customizations to the client
        .addInterceptor(loggingInterceptor)
        //.addInterceptor(chuckerInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://kub.terrasofthq.com/api/beneficiary-app/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    //can add more instances here eg //val authApi: NotesApi = retrofit.create(NotesApi::class.java)

}