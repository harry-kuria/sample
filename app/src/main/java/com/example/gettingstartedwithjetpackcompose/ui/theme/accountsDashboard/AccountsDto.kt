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
    val notes: String?,
)

// pass account uuid as param and extra info as a boolean param
// use these variable names for your request or payload
// account_id = uuid
// extra_info =(boolean)

//wallets vars
//name, currency, balance, default(can be 0 or 1 but display as true or false
//dependant vars to have name and age of dependant and status if active or not
//iots vars to have serial number, status and assignee
// under iots assignee, have name only
// hint: assignee is under an iot so should be a data class called under iot data class