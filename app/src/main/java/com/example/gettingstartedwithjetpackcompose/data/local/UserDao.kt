package com.example.gettingstartedwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    //@Insert defines how the database should add in information
    //In parentheses: when the user enters something that they already entered before, it should throw an error(ABORT)
    //It can also REPLACE, IGNORE or ROLLBACK(goes back to how the data was before the transaction(no partial saves)
    //Does this mean it will delete everything in the database and start from scratch?
    suspend fun insertUser(user: User) //inserts user of type class
    //suspend just allows this to not be run on the main program/ thread. pauses and resumes without blocking the app
    //accessing databases is more time consuming so this could lead to errors if suspend is not used
    //called using viewModel or CoroutineScope if not error


    @Query("SELECT * FROM Users WHERE email = :email LIMIT 1")
    //select all data from the table Users where the email is going to be gotten from the findByEmail parameters earlier(:email(SQL)) and stop looking immediately you find the email needed
    suspend fun findByEmail(email: String): User?

    @Query("""SELECT * FROM users WHERE email = :email AND passwordHash = :password LIMIT 1""")
    suspend fun login(email: String, password: String): User?
}