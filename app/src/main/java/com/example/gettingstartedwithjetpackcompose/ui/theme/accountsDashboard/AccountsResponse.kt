package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import com.google.gson.annotations.SerializedName

data class AccountsResponse<T>(
    val status : Int,
    val data :  T, //PaginatedAccountsData,
    val message : String
)

data class PaginatedAccountsData(
    @SerializedName("current_page") val currentPage : Int,
    val data : List<AccountsDto>
)