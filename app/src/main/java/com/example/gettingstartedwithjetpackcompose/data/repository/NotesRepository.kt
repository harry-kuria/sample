package com.example.gettingstartedwithjetpackcompose.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import com.example.gettingstartedwithjetpackcompose.data.local.Note
import com.example.gettingstartedwithjetpackcompose.data.local.NotesDao
import com.example.gettingstartedwithjetpackcompose.data.local.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao, private val userDao: UserDao,
                                          private val dataStore: DataStore<UserAccountData>
){

    val userData: Flow<UserAccountData> = dataStore.data

    //val notes : Flow<List<Note>> = notesDao.getUserNotes(userId)
    fun getUserNotes(userId: Long): Flow<List<Note>> {
        return notesDao.getUserNotes(userId)
    }

    suspend fun getNote(userId: Long, id: Long) = notesDao.getNoteById(userId, id)

    fun search(userId: Long,query: String) = notesDao.searchUserNotes(userId,query)

//    suspend fun createNote(note: Note) : Long {
//        val user = userDao.findById(note.userId)
//        if (user != null) {
//            notesDao.createNewNote(note)
//        }else{
//            //throw Exception("User not found")
//            Log.e("NotesRepository", "User with id ${note.userId} not found")
//        }
//        return note.id
//    }

    suspend fun createNote(note: Note): Long {
        val user = userDao.findById(note.userId)
        return if (user != null) {
            val id = notesDao.createNewNote(note)
            Log.d("NotesRepository", "Note created with id $id for user ${note.userId}")
            id
        } else {
            Log.e("NotesRepository", "User with id ${note.userId} not found")
            -1L
        }
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