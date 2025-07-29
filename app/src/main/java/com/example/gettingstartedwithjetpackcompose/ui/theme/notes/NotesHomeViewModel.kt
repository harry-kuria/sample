package com.example.gettingstartedwithjetpackcompose.ui.theme.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.local.Note
import com.example.gettingstartedwithjetpackcompose.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.filter
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first


@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@HiltViewModel
class NotesHomeViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentUserId = MutableStateFlow(0L)
    val currentUserId: StateFlow<Long> = _currentUserId.asStateFlow()

    // Note editing state
    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    private val _noteTitle = MutableStateFlow("")
    val noteTitle: StateFlow<String> = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow("")
    val noteContent: StateFlow<String> = _noteContent.asStateFlow()

    private var autoSaveJob: Job? = null

    val searchResults: Flow<List<Note>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(emptyList<Note>())
            } else {
                currentUserId
                    .filter { it > 0 }
                    .flatMapLatest { userId ->
                        notesRepository.search(userId, query)
                    }
            }
        }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


    val notes: Flow<List<Note>> = currentUserId
        .filter { it > 0 } // Only proceed if we have a valid user ID
        .flatMapLatest { userId ->
            notesRepository.getUserNotes(userId)
        }
    init {
        // Initialize user session
        viewModelScope.launch {
            notesRepository.userData.collect { userData ->
                _currentUserId.value = userData.id
            }
        }
    }

    fun createEmptyNote(onCreated: (Long) -> Unit) {
        viewModelScope.launch {
            val userId = currentUserId.value
            if (userId > 0) {
                val note = Note(
                    userId = userId,
                    title = "",
                    content = "",
                    timestamp = System.currentTimeMillis()
                )
                val id = notesRepository.createNote(note)
                onCreated(id)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.delete(note)
        }
    }


    fun loadNote(id: Long) {
        viewModelScope.launch {
            val currentUserId = notesRepository.userData.first().id
            val foundNote = notesRepository.getNote(currentUserId, id)
            foundNote?.let {
                _note.value = it
                _noteTitle.value = it.title
                _noteContent.value = it.content
            }
        }
    }

    fun updateTitle(title: String) {
        _noteTitle.value = title
        triggerAutoSave()
    }

    fun updateContent(content: String) {
        _noteContent.value = content
        triggerAutoSave()
    }

    fun triggerAutoSave() {
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            delay(500) // 0.5 second debounce
            saveCurrentNote()
        }
    }

    private suspend fun saveCurrentNote() {
        val currentNote = _note.value ?: return
        val userId = notesRepository.userData.first().id
        val updatedNote = currentNote.copy(
            title = _noteTitle.value,
            content = _noteContent.value,
            timestamp = System.currentTimeMillis()
        )
        notesRepository.edit(updatedNote)
        _note.value = updatedNote
    }

    fun clearCurrentNote() {
        _note.value = null
        _noteTitle.value = ""
        _noteContent.value = ""
        autoSaveJob?.cancel()
    }
}