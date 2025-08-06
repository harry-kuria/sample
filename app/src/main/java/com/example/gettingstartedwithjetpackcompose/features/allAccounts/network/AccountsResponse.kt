package com.example.gettingstartedwithjetpackcompose.features.allAccounts.network

import com.google.gson.annotations.SerializedName

data class AccountsResponse<T>(
    val status : Int,
    val data :  PagedData<T>,
    val message : String
)

data class PagedData<T>(
    @SerializedName("current_page") val currentPage: Int,
    val data: List<T>
)