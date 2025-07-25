package com.example.gettingstartedwithjetpackcompose.data.model

import android.content.Context
import androidx.room.Room
import com.example.gettingstartedwithjetpackcompose.data.local.AppDatabase
import com.example.gettingstartedwithjetpackcompose.data.local.UserDao
import com.example.gettingstartedwithjetpackcompose.data.local.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration(true)
            //.fallbackToDestructiveMigrationOnDowngrade()
            .build()

        //Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

        @Provides
        fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.UserDao()

        @Provides
        fun provideNotesDao(appDatabase: AppDatabase): NotesDao = appDatabase.NotesDao()
}