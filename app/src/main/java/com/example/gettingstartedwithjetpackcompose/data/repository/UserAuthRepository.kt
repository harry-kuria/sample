package com.example.gettingstartedwithjetpackcompose.data.repository

import androidx.datastore.core.DataStore
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import com.example.gettingstartedwithjetpackcompose.data.local.UserDao
import com.example.gettingstartedwithjetpackcompose.data.local.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserAuthRepository @Inject constructor(private val userDao: UserDao,
                                             private val dataStore: DataStore<UserAccountData>
){
    val userData: Flow<UserAccountData> = dataStore.data

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { accountData -> accountData.isLoggedIn }

    val userId: Flow<Long> = dataStore.data.map { it.id }

    val email: Flow<String> = dataStore.data.map { accountData -> accountData.email }

    val username: Flow<String> = dataStore.data.map { accountData -> accountData.username }


    suspend fun getUserId(): Long = dataStore.data.first().id


    //Functions for room database
    suspend fun getUserByEmail(email: String): User?{
        return userDao.findByEmail(email)
    }

    suspend fun getUserById(id: Long): User?{
        return userDao.findById(id)
    }

    suspend fun isSessionValid(): Boolean {
        val userId = getUserId()
        return userDao.findById(userId) != null
    }

    suspend fun loginUser(email:String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun registerUser(user: User) : Long {
        val id = userDao.insertUser(user)
        saveUserAccountData(id = id, email = user.email, username = user.username)
        return id
    }

    suspend fun updateLoggedInStatus(id : Long, isLoggedIn: Boolean) {
        userDao.updateIsLoggedIn(id = id, isLoggedIn = isLoggedIn)
    }

//    suspend fun deleteAllUsers() {
//        userDao.deleteAll()
//    }

//    suspend fun setLoggedIn(isLoggedIn: Boolean) {
//        dataStore.updateData { it.toBuilder()
//            .setIsLoggedIn(isLoggedIn)
//            .build() }
//    }



//DATASTORE FUNCTIONS
    suspend fun saveUserAccountData(id: Long, email: String, username: String){
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setId(id)
                .setEmail(email)
                .setUsername(username)
                .setIsLoggedIn(true)
                .build()
        }
    }

    suspend fun clearUserAccountData(){
        dataStore.updateData { currentData -> currentData.toBuilder()
            .clearId()
            .clearEmail()
            .clearUsername()
            .setIsLoggedIn(false)
            .build() }
    }

//    fun isUserLoggedIn(): Flow<Boolean> = userData.map { it.isLoggedIn }
//    fun getUserEmail(): Flow<String> = userData.map { it.email }
//    fun getUsername(): Flow<String> = userData.map { it.username }
}