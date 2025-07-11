package com.example.gettingstartedwithjetpackcompose.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

////Use kapt for now
@Entity(tableName = "Users", indices = [Index(value = ["email"], unique = true)])
//indices = ... ensures that al the email address values are the same and throws an error if not
data class User(
    //@ColumnInfo(name = "password") val passwordHash: String, //keeps the name of password field as password but variable name as passHash
    @PrimaryKey val email: String, //sets this as the primary key
    //Declaring composite PKs is done within the parentheses of @Entity() and uses pK=[...] not @PK...
    val username: String,
    val passwordHash: String,
    val isLoggedIn: Boolean
)
