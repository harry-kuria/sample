package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import javax.inject.Inject

class AccountsRepository @Inject constructor(private val accountsApi : AccountsAPI) {
    suspend fun getAccounts() : List<AccountsDto> {
        val response = accountsApi.getAccounts()
        if (response.isSuccessful) {
            return response.body()?.data ?.data?: emptyList()
        } else {
            throw Exception("Failed to load accounts: ${response.code()} - ${response.message()}")
        }
    }
}