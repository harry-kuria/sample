package com.example.gettingstartedwithjetpackcompose.data.repository

import androidx.datastore.core.DataStore
import com.example.gettingstartedwithjetpackcompose.data.local.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton



//Directly interacts with the datastore

@Singleton
class UserDataStoreRepository @Inject constructor(
    private val dataStore: DataStore<User>

){
    val userData: Flow<User> = dataStore.data
    suspend fun saveUserAccountData(email: String, username: String){
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setEmail(email)
                .setUsername(username)
                .setIsLoggedIn(true)
                .build()
        }
    }

    suspend fun logout(){
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