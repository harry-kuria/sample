package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import retrofit2.Response
import retrofit2.http.GET

interface AccountsAPI {
    @GET("sync/accounts")
    suspend fun getAccounts(): Response<AccountsResponse>
//if the api returns a raw list. as in id, name ... not status, data[id, name...] if not raw
}