package com.example.gettingstartedwithjetpackcompose.data.network.accountsNetwork

import com.google.gson.annotations.SerializedName

data class FullAccountDetailsDto(
    val names: String?,
    val uuid: String?,
    val phone: String?,
    val gender: String?,
    val code: String?,
    @SerializedName("kyc_status") val kycStatus: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val notes: String?,

    val wallets: List<WalletDto> = emptyList(),
    val dependants: List<DependantDto> = emptyList()
)

data class WalletDto(
    val name: String?,
    val currency: String?,
    val balance: Double?,
    @SerializedName("default") val isDefault: Int?
)

data class DependantDto(
    val names: String?,
    val gender: String?,
    val dob: String?,
    val age: String?,
    val iots: List<IotDto> = emptyList()
)

data class IotDto(
    @SerializedName("serial_number") val serialNumber: String?,
    val status: Boolean?,
    val assignee: AssigneeDto?
)

data class AssigneeDto(
    val name: String?
)
