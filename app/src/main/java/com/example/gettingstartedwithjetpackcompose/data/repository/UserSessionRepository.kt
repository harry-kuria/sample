package com.example.gettingstartedwithjetpackcompose.data.repository

import com.example.gettingstartedwithjetpackcompose.UserAccountData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Layer between ViewModel and DataStore

class UserSessionRepository @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    val userData: Flow<UserAccountData> = userDataStoreRepository.userData

//    fun isUserLoggedIn(): Flow<Boolean> = userDataStoreRepository.isUserLoggedIn()
//
//    fun getUserEmail(): Flow<String> = userDataStoreRepository.getUserEmail()
//
//    fun getUsername(): Flow<String> = userDataStoreRepository.getUsername()

    suspend fun login(email: String, username: String) {
        userDataStoreRepository.saveUserAccountData(email, username)
    }

    suspend fun logout() {
        userDataStoreRepository.logout()
    }
}