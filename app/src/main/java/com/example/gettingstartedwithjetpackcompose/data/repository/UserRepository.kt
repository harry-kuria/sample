package com.example.gettingstartedwithjetpackcompose.data.repository

import com.example.gettingstartedwithjetpackcompose.data.local.UserDao
import com.example.gettingstartedwithjetpackcompose.data.local.User


class UserRepository(private val userDao: UserDao){
    suspend fun login(email:String, password: String): User? =
        userDao.login(email, password)

    suspend fun register(user: User): Result<Unit> = runCatching { //Result<Unit> returns nothing like a "procedure" not a function. this is good because you don't have to catch exceptions
        userDao.insertUser(user)
    }
}