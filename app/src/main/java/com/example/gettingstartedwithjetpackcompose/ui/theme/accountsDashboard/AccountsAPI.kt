package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountsAPI {
    @GET("sync/accounts")
    suspend fun getAccounts(@Query ("search") query: String? = null): Response<AccountsResponse>
//if the api returns a raw list. as in id, name ... not status, data[id, name...] if not raw

    //account_id
    //extra_info should be boolean
}