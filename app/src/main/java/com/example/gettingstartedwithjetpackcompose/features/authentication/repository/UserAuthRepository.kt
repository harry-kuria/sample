package com.example.gettingstartedwithjetpackcompose.features.authentication.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import com.example.gettingstartedwithjetpackcompose.data.local.User
import com.example.gettingstartedwithjetpackcompose.data.local.UserDao
import com.example.gettingstartedwithjetpackcompose.data.network.ApiClient
import com.example.gettingstartedwithjetpackcompose.features.authentication.network.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAuthRepository @Inject constructor(private val userDao: UserDao,
                                             private val dataStore: DataStore<UserAccountData>
){
    val userData: Flow<UserAccountData> = dataStore.data

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { accountData -> accountData.isLoggedIn }

    val email: Flow<String> = dataStore.data.map { accountData -> accountData.email }

    val username: Flow<String> = dataStore.data.map { accountData -> accountData.username }


    suspend fun getUserId(): Long = dataStore.data.first().id


    //Functions for room database
    suspend fun getUserByEmail(email: String): User?{
        return userDao.findByEmail(email)
    }

//    suspend fun getUserById(id: Long): User?{
//        return userDao.findById(id)
//    }
//
//    suspend fun isSessionValid(): Boolean {
//        val userId = getUserId()
//        return userDao.findById(userId) != null
//    }

    suspend fun loginUser(email:String, pin: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                //calls the API
                val response = ApiClient.authApi.login(
                    //deviceSerial = "1234567890",
                    request = LoginRequest(email = email, pin = pin)
                )

                if (response.isSuccessful) { //between 200 and 299
                    val body = response.body()

                    if (body != null) {
                        val user = User(
                            id = body.data.id, email = body.data.email,
                            username = body.data.username, passwordHash = pin, isLoggedIn = true
                        )
                        userDao.login(email, pin)
                        return@withContext Result.success(user) // returns
                    } else {
                        return@withContext Result.failure(Exception("Login failed: Server responded with success but sent no data"))
                    }
                } else {
                    return@withContext Result.failure(Exception("Login failed: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("LoginRepository", "Login failed: ${e.message}", e)
                val localUser = userDao.login(email, pin)
                return@withContext if (localUser != null) {
                    Result.success(localUser)
                } else {
                    Result.failure(Exception("Login failed: offline mode and no match"))
                }
            }
        }
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