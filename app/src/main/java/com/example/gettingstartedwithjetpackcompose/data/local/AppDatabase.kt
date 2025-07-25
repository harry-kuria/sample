package com.example.gettingstartedwithjetpackcompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Note::class], version = 5)
abstract class AppDatabase: RoomDatabase(){
    abstract fun UserDao(): UserDao
    abstract fun NotesDao(): NotesDao

    companion object{ // allows for accessing objects in the class without getters (like static in Java)
        @Volatile //stops different threads in the program from reading the same information which can lead to memory waste
        private var INSTANCE: AppDatabase? = null // creates an instance of the database without copying multiple databases
//        private val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE User ADD COLUMN isLoggedIn INTEGER NOT NULL DEFAULT 0")
//            //SQL doesn't use boolean so we use 0 for false and 1 for true
//            }
//        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this){ //returns INSTANCE if not null else go to the right side(?:)
                Room.databaseBuilder( //function used to create the database
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                    //.addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration(true)
                    //.fallbackToDestructiveMigrationOnDowngrade(true)
                    .build().also {INSTANCE = it} //builds the database and then adds it to instance variable
            }
        }
    }//push test
}