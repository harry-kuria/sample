package com.example.gettingstartedwithjetpackcompose.data.repository

import androidx.datastore.core.DataStore
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import com.example.gettingstartedwithjetpackcompose.data.local.Note
import com.example.gettingstartedwithjetpackcompose.data.local.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao,
                                          private val dataStore: DataStore<UserAccountData>
){

    val userData: Flow<UserAccountData> = dataStore.data

    val notes : Flow<List<Note>> = notesDao.getAllNotes()

    suspend fun getNote(id: Long) = notesDao.getNoteById(id)

    fun search(query: String) = notesDao.searchNotes(query)

    suspend fun createNote(note: Note) : Long {
        return notesDao.createNewNote(note)
    }
    suspend fun edit(note: Note) = notesDao.editNote(note)

    suspend fun delete(note: Note) = notesDao.deleteNote(note)

    suspend fun setLastOpenedNoteId(noteId: Long) {
        dataStore.updateData { current ->
            current.toBuilder()
                .setLastOpenedNoteId(noteId)
                .build()
        }
    }

    suspend fun clearLastOpenedNoteId() {
        dataStore.updateData { current ->
            current.toBuilder()
                .setLastOpenedNoteId(-1L)
                .build()
        }
    }
}