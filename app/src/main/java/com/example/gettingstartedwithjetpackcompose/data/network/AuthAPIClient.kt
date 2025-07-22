package com.example.gettingstartedwithjetpackcompose.data.network

//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import com.example.gettingstartedwithjetpackcompose.data.network.AuthAPI
//
//
//object AuthAPIClient{
//    //for debugging
//    val logging = HttpLoggingInterceptor().apply{ //the HttpLoggingInterceptor function is used to return what a json object. basically it takes the user input and returns it to the log which can help with debugging
//        if (BuildConfig.DEBUG) { //checks if the app is in debug mode so that the app isn't constantly exposed to user data
//            level = HttpLoggingInterceptor.Level.BODY //returns the json object if the app is in debug mode .BODY returns everything
//        }else{
//            level = HttpLoggingInterceptor.Level.NONE //returns nothing .NONE
//        }
//    }
//
//    val client = OkHttpClient.Builder()
//        //app needs OkHTTPClient to connect to the server as it acts as a handler
//        //OkHttpClient makes the request to the server
//        //.Builder() adds functionalities/ customises the client adding features like the logging, timing
//        .addInterceptor(logging)
//        //this is basically saying that for all the requests and responses made you should return the json object to the log
//        .build() //completes the build
//
//    val retrofit = Retrofit.Builder()
//        //Retrofit makes API calls easily. This line is the starting point to configure how we want to handle the requests
//        .baseUrl("https://............./") //must end with a slash
//        //starting point of all requests
//        .client(client)
//        //tells retrofit to use custom client which in this case includes logging
//        //adds authorisation tokens
//        .addConverterFactory(GsonConverterFactory.create())
//        //converts the json object to a kotlin object
//        .build()
//
//    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
//
//}