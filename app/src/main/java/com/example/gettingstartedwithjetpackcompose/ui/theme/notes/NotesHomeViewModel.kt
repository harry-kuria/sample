package com.example.gettingstartedwithjetpackcompose.ui.theme.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.UserAccountData
import com.example.gettingstartedwithjetpackcompose.data.local.Note
import com.example.gettingstartedwithjetpackcompose.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject


@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@HiltViewModel
class NotesHomeViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
): ViewModel() {

    val userAccountData: StateFlow<UserAccountData> =
        notesRepository.userData
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UserAccountData.getDefaultInstance()
            )


    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note

    val notes = notesRepository.notes.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    //stateIn converts the flow to a stateFlow so that it can be observed
    // SharingStarted.Lazily means that the flow will only start to get values when the notes screen is started so as to save resource. While its not sharing use an emptyList()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery : StateFlow<String> = _searchQuery

    private val _noteTitle = MutableStateFlow("")
    val noteTitle : StateFlow<String> = _noteTitle

    private val _noteContent = MutableStateFlow("")
    val noteContent : StateFlow<String> = _noteContent

    fun updateTitle(title: String) {
        _noteTitle.value = title
    }

    fun updateContent(content: String) {
        _noteContent.value = content
    }

    //saves the note as user is typing
    init {
        combine(_noteTitle, _noteContent) { title, content ->
            Pair(title, content)} // returns a pair of title and content
            .debounce(500)
            // wait 500ms after last change (in case user is typing fast)
            .onEach { pair: Pair<String, String> ->
                val (title, content) = pair
                if (title.isNotBlank()) {
                    viewModelScope.launch {
                        if (note.value != null) {
                            notesRepository.edit(note.value!!.copy(title = title, content = content))
                        } else{
                            val id = notesRepository.createNote(Note(title = title, content = content))
                            _note.value = notesRepository.getNote(id)
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadNote(id: Long) {
        viewModelScope.launch {
            notesRepository.setLastOpenedNoteId(id)
            val foundNote = notesRepository.getNote(id)
            _note.value = foundNote
            _noteTitle.value = foundNote?.title ?: ""
            _noteContent.value = foundNote?.content ?: ""
        }
    }

    val searchResults: StateFlow<List<Note>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(emptyList())
            } else {
                notesRepository.search(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesRepository.createNote(note)
        }
    }


    fun createEmptyNote(onNoteCreated: (Long) -> Unit) {
        viewModelScope.launch {
            val id = notesRepository.createNote(Note(title = "", content = ""))
            onNoteCreated(id)
        }
    }


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.delete(note)
        }
    }

    fun editNote(note: Note) {
        viewModelScope.launch {
            notesRepository.edit(note)
        }
    }

    fun clearSearchResult() {
        _searchQuery.value = ""
    }

    fun updateLastOpenedNoteId(id: Long) {
        viewModelScope.launch {
            notesRepository.setLastOpenedNoteId(id)
        }
    }

    fun clearLastOpenedNoteId() {
        viewModelScope.launch {
            notesRepository.clearLastOpenedNoteId()
        }
    }

    fun clearCurrentNote(){
        _note.value = null
        _noteTitle.value = ""
        _noteContent.value = ""
    }
}
