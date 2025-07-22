package com.example.gettingstartedwithjetpackcompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao{
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Long): Note?

    //RETURNS THE ID OF THE INSERTED NOTE
    @Insert//(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createNewNote(note: Note) : Long

    @Update
    suspend fun editNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}