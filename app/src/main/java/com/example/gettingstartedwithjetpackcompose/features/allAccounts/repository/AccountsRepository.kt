package com.example.gettingstartedwithjetpackcompose.features.allAccounts.repository

import android.util.Log
import com.example.gettingstartedwithjetpackcompose.features.allAccounts.network.AccountsAPI
import com.example.gettingstartedwithjetpackcompose.features.allAccounts.network.AccountsDto
import com.example.gettingstartedwithjetpackcompose.features.allAccounts.network.FullAccountDetailsDto
import com.google.gson.Gson
import javax.inject.Inject

class AccountsRepository @Inject constructor(private val accountsApi : AccountsAPI) {
    suspend fun getAccounts(query : String? = null) : List<AccountsDto> {
        val response = accountsApi.getAccounts(query)
        if (response.isSuccessful) {
            return response.body()?.data?.data ?: emptyList()
        } else {
            throw Exception("Failed to load accounts: ${response.code()} - ${response.message()}")
        }
    }


    suspend fun getFullAccountDetails(uuid: String, extraInfo: Boolean): FullAccountDetailsDto? {
        val response = accountsApi.getFullAccountDetails(uuid, extraInfo)
        if (response.isSuccessful) {
            val body = response.body()
            val account = body?.data?.data?.firstOrNull()
            Log.d("DETAILS_API", "Parsed account: ${Gson().toJson(account)}")
            return account
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("DETAILS_API", "Error: ${response.code()} - $errorBody")
            throw Exception("Failed to load account details. Code: ${response.code()}")
        }
    }
}