package com.example.gettingstartedwithjetpackcompose.data.repository

import androidx.datastore.core.DataStore
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton



//Directly interacts with the datastore

@Singleton
class UserDataStoreRepository @Inject constructor(
    private val dataStore: DataStore<UserAccountData>

){
    val userData: Flow<UserAccountData> = dataStore.data
    val isLoggedIn: Flow<Boolean> = dataStore.data.map { accountData -> accountData.isLoggedIn }

    val email: Flow<String> = dataStore.data.map { accountData -> accountData.email }

    val username: Flow<String> = dataStore.data.map { accountData -> accountData.username }

//    suspend fun setLoggedIn(isLoggedIn: Boolean) {
//        dataStore.updateData { it.toBuilder()
//            .setIsLoggedIn(isLoggedIn)
//            .build() }
//    }

    suspend fun saveUserAccountData(email: String, username: String){
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setEmail(email)
                .setUsername(username)
                .setIsLoggedIn(true)
                .build()
        }
    }

    suspend fun clearUserAccountData(){
        dataStore.updateData { currentData -> currentData.toBuilder()
            .clearEmail()
            .clearUsername()
            .setIsLoggedIn(false)
            .build() }
    }

//    fun isUserLoggedIn(): Flow<Boolean> = userData.map { it.isLoggedIn }
//    fun getUserEmail(): Flow<String> = userData.map { it.email }
//    fun getUsername(): Flow<String> = userData.map { it.username }
}