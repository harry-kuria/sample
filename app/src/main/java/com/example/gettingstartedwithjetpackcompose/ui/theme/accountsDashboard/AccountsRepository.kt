package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import android.util.Log
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


    suspend fun getFullAccountDetails(uuid: String, extraInfo: Boolean): FullAccountDetailsDto {
        val response = accountsApi.getFullAccountDetails(uuid = uuid, extraInfo = true)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.data != null) {
                Log.d("DEBUG", "Account details loaded: ${body.data}")
                return body.data
            } else {
                Log.e("DEBUG", "Empty data: ${body?.message}")
                throw Exception("Empty account details: ${body?.message}")
            }
        } else {
            Log.e("DEBUG", "Response error: ${response.code()} ${response.message()}")
            throw Exception("Failed to load account: ${response.code()} ${response.message()}")
        }
    }
//suspend fun getFullAccountDetails(uuid: String, extraInfo: Boolean): FullAccountDetailsDto? {
//    return try {
//        val response = accountsApi.getFullAccountDetails(uuid = uuid, extraInfo = true)
//        response.data.data.firstOrNull() // pick the first item from the list
//    } catch (e: Exception) {
//        Log.e("DETAILS_API", "Error fetching account details", e)
//        null
//    }
//    val responseBody = accountsApi.getFullAccountDetails(uuid, extraInfo)
//    val rawJson = responseBody.string()
//    Log.d("DETAILS_RAW", "Raw JSON: $rawJson")
//    return Gson().fromJson(rawJson, FullAccountDetailsDto::class.java)
//}

}