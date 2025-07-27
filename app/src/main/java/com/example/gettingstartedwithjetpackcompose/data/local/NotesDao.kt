package com.example.gettingstartedwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes WHERE userId = :userId ORDER BY timestamp DESC")
    fun getUserNotes(userId: Long): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE userId = :userId AND title LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchUserNotes(userId: Long, query: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id AND userId = :userId LIMIT 1")
    suspend fun getNoteById(userId: Long, id: Long): Note?

    @Insert
    suspend fun createNewNote(note: Note): Long //returns the notes id

    @Update(entity = Note::class)
    suspend fun editNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
