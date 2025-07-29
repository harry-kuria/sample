package com.example.gettingstartedwithjetpackcompose.data.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.gettingstartedwithjetpackcompose.MyApplication
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

    private val chuckerCollector = ChuckerCollector(
        context = MyApplication.appContext,
        showNotification = true, //allows to see logs in notification. set to false when in production to keep sensitive information from users
        retentionPeriod = RetentionManager.Period.ONE_DAY //keeps logs for one day before deleting
    )

    private val chuckerInterceptor = ChuckerInterceptor.Builder(MyApplication.appContext)
        .collector(chuckerCollector)
        .maxContentLength(250000L)
        .redactHeaders("AuthToken") //hide sensitive headers from users shows ***
        .alwaysReadResponseBody(true)
        //.addBodyDecoder(decoder) //decodes the body
        .createShortcut(true) //allows to create a shortcut to chucker without having to go through the app itself
        .build()

    private val client = OkHttpClient.Builder()
        //app needs OkHTTPClient to connect to the server as it acts as a handler
        //making customizations to the client

        //through this, we have one source of truth
        .addInterceptor(loggingInterceptor)
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer 50888|eOlfVR9LwpbwAxh9QTLNPaU8NZivNFXZbdcc3NSW2998c845")
                .addHeader("app-key", "97fb852f-f24d-49d5-9375-6825bde2b3de")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("device-serial", "1234567890")
                .addHeader("device-fingerprint", "184e757995907383285ca8faf5ce5f30")
                .build()
            it.proceed(request)
        }
        .addInterceptor(chuckerInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://kub.terrasofthq.com/api/beneficiary-app/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    //can add more instances here eg //val authApi: NotesApi = retrofit.create(NotesApi::class.java)

}