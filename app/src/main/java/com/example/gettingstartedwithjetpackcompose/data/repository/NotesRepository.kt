package com.example.gettingstartedwithjetpackcompose.data.repository

import com.example.gettingstartedwithjetpackcompose.data.local.Note
import com.example.gettingstartedwithjetpackcompose.data.local.NotesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao
){
    val notes : Flow<List<Note>> = notesDao.getAllNotes()

    suspend fun getNote(id: Long) = notesDao.getNoteById(id)

    fun search(query: String) = notesDao.searchNotes(query)

    suspend fun createNote(note: Note) : Long {
        return notesDao.createNewNote(note)
    }
    suspend fun edit(note: Note) = notesDao.editNote(note)

    suspend fun delete(note: Note) = notesDao.deleteNote(note)

}