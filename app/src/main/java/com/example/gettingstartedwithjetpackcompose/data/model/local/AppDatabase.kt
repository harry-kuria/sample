package com.example.gettingstartedwithjetpackcompose.data.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun UserDao(): UserDao
    //abstract fun userDao()

    companion object{ // allows for accessing objects in the class without getters (like static in Java)
        @Volatile //stops different threads in the program from reading the same information which can lead to memory waste
        private var INSTANCE: AppDatabase? = null // creates an instance of the database without copying multiple databases
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this){ //returns INSTANCE if not null else go to the right side(?:)
                Room.databaseBuilder( //function used to create the database
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database").build().also {INSTANCE = it} //builds the database and then adds it to instance variable
            }
        }

    }
}
