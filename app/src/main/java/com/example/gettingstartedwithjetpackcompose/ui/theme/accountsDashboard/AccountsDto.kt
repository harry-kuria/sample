package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import com.google.gson.annotations.SerializedName

data class AccountsDto(
    val names: String,
    val uuid: String,
    val phone: String,
    val gender: String,
    val code: String,
    @SerializedName("kyc_status") val kycStatus: String, // sort of maps the variables
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val notes: String?
)