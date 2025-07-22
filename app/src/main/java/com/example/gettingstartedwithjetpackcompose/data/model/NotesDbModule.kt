package com.example.gettingstartedwithjetpackcompose.data.model

import android.content.Context
import androidx.room.Room
import com.example.gettingstartedwithjetpackcompose.data.local.NotesDao
import com.example.gettingstartedwithjetpackcompose.data.local.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesDbModule{
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database").build()
    }

    @Provides
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao {
        return notesDatabase.NotesDao()
    }
}