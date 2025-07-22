package com.example.gettingstartedwithjetpackcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun NotesDao(): NotesDao
//    private val MIGRATION_1_2 = object : Migration(2, 3) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE User ADD COLUMN isLoggedIn INTEGER NOT NULL DEFAULT 0")
//            //SQL doesn't use boolean so we use 0 for false and 1 for true
//        }
//    }
}