package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import com.google.gson.annotations.SerializedName

data class AccountsResponse(
    val status : Int,
    val data : PaignatedAccountsData,
    val message : String
)

data class PaignatedAccountsData(
    @SerializedName("current_page") val currentPage : Int,
    val data : List<AccountsDto>
)