package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import com.google.gson.annotations.SerializedName

data class FullAccountDetailsDto(
    val names: String?,
    val uuid: String?,
    val phone: String?,
    val gender: String?,
    val code: String?,
    @SerializedName("kyc_status") val kycStatus: String?, // sort of maps the variables
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val notes: String?,

    //extra info
    val wallets: List<Wallets> = emptyList(),
    val dependants: List<Dependants> = emptyList(),
    //val iots: List<Iots> = emptyList()
)

data class Wallets(
    val name: String?,
    val currency: String?,
    val balance: Double?,
    @SerializedName("default") val isDefault: Int?,
    //val iots: List<Iots> = emptyList()
)

data class Dependants(
    val names: String?,
    val age: String?,
    //val status: String
    val iots : List<Iots> = emptyList()
)

data class Iots(
    @SerializedName("serial_number") val serialNumber: String,
    val status: String?,
    val assignee: Assignee?
)

data class Assignee(
    val name: String
)




data class FullAccountResponse(
    val status: Int,
    val data: FullAccountDataWrapper
)

data class FullAccountDataWrapper(
    @SerializedName("current_page") val currentPage: Int,
    val data: List<FullAccountDetailsDto>
)