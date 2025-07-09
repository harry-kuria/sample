package com.example.gettingstartedwithjetpackcompose.data.repository

import com.example.gettingstartedwithjetpackcompose.data.local.UserDao
import com.example.gettingstartedwithjetpackcompose.data.local.User
import javax.inject.Inject


class UserRepository @Inject constructor(private val userDao: UserDao){
    suspend fun getUserByEmail(email: String): User?{
        return userDao.findByEmail(email)
    }

    suspend fun loginUser(email:String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

//    suspend fun deleteAllUsers() {
//        userDao.deleteAll()
//    }
}