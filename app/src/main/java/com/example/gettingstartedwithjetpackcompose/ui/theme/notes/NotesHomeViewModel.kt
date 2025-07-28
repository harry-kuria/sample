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
//@HiltViewModel
//class NotesHomeViewModel @Inject constructor(
//    private val notesRepository: NotesRepository,
//): ViewModel() {
//
//    val userAccountData: StateFlow<UserAccountData> =
//        notesRepository.userData
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000),
//                initialValue = UserAccountData.getDefaultInstance()
//            )
//
//
//    private val _note = MutableStateFlow<Note?>(null)
//    val note: StateFlow<Note?> = _note
//
//    //stateIn converts the flow to a stateFlow so that it can be observed
//    // SharingStarted.Lazily means that the flow will only start to get values when the notes screen is started so as to save resource. While its not sharing use an emptyList()
//    val notes : StateFlow<List<Note>> = userAccountData.filter { it.id != 0L }
//        .flatMapLatest { user -> notesRepository.getUserNotes(user.id) }
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//
//    private val _searchQuery = MutableStateFlow("")
//    val searchQuery : StateFlow<String> = _searchQuery
//
//    private val _noteTitle = MutableStateFlow("")
//    val noteTitle : StateFlow<String> = _noteTitle
//
//    private val _noteContent = MutableStateFlow("")
//    val noteContent : StateFlow<String> = _noteContent
//
//    fun updateTitle(title: String) {
//        Log.d("NoteVM", "updateTitle called: $title")
//        _noteTitle.value = title
//    }
//
//    fun updateContent(content: String) {
//        _noteContent.value = content
//    }
//
//    init {
//        viewModelScope.launch {
//            userAccountData.collect {
//                Log.d("NotesHomeViewModel", "Collected User ID: ${it.id}, email: ${it.email}")
//            }
//        }
//    }
//
//    //saves the note as user is typing
////    init {
////        val currentUserId = userAccountData.value.id
////        combine(_noteTitle, _noteContent) { title, content ->
////            Pair(title, content)} // returns a pair of title and content
////            .debounce(500)
////            // wait 500ms after last change (in case user is typing fast)
////            .onEach { pair: Pair<String, String> ->
////                val (title, content) = pair
////                if (title.isNotBlank()) {
////                    viewModelScope.launch {
////                        if (note.value != null) {
////                            notesRepository.edit(note.value!!.copy(title = title, content = content))
////                        } else{
////                            val id = notesRepository.createNote(Note(userId = currentUserId, title = title, content = content))
////                            _note.value = notesRepository.getNote(userId = currentUserId, id = id)
////                        }
////                    }
////                }
////            }
////            .launchIn(viewModelScope)
////    }
//
////    fun startAutoSave() {
////        autoSaveJob?.cancel() // cancel old job if exists
////
////        autoSaveJob = combine(_noteTitle, _noteContent) { title, content ->
////            Pair(title, content)
////        }
////            //.debounce(500)
////            .onEach { (title, content) ->
////                if (title.isNotBlank() && note.value != null) {
////                    val updated = note.value!!.copy(title = title, content = content)
////                    notesRepository.edit(updated)
////                }
////                Log.d("AutoSave", "Saving note: $title | $content")
////            }
////            .launchIn(viewModelScope)
////    }
//
//    var autoSaveJob: Job? = null
//        private set
//
//    fun startAutoSave() {
//        autoSaveJob?.cancel()
//
//        autoSaveJob = combine(_noteTitle, _noteContent) { title, content ->
//            title to content
//        }
//            .debounce(500)
//            .onEach { (title, content) ->
//                val currentNote = note.value ?: run {
//                    Log.d("AutoSave", "No current note to save")
//                    return@onEach
//                }
//
//                if (title.isNotBlank()) {
//                    val updatedNote = currentNote.copy(
//                        title = title,
//                        content = content,
//                        timestamp = System.currentTimeMillis() // Ensure timestamp updates
//                    )
//
//                    Log.d("AutoSave", "Saving note: ${updatedNote.id} | ${updatedNote.title}")
//                    notesRepository.edit(updatedNote)
//                    _note.value = updatedNote // Update local state
//                }
//            }
//            .launchIn(viewModelScope)
//    }
//
//
//    fun loadNote(id: Long) {
//        val currentUserId = userAccountData.value.id
//        viewModelScope.launch {
//            notesRepository.setLastOpenedNoteId(id)
//            val foundNote = notesRepository.getNote(userId = currentUserId, id = id)
//            foundNote?.let { note ->
//                _note.value = note
//                // Only update if different to avoid infinite loops
//                if (_noteTitle.value != note.title) _noteTitle.value = note.title
//                if (_noteContent.value != note.content) _noteContent.value = note.content
//                Log.d("NotesHomeViewModel", "Loaded note: ${note.id} | ${note.title}")
//            }
//        }
//    }
//
//    val searchResults: StateFlow<List<Note>> = _searchQuery
//        .flatMapLatest { query ->
//            if (query.isBlank()) {
//                flowOf(emptyList())
//            } else {
//                notesRepository.search(userId = userAccountData.value.id, query = query)
//            }
//        }
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//
//    fun updateSearchQuery(query: String) {
//        _searchQuery.value = query
//    }
//
//    fun addNote(note: Note) {
//        viewModelScope.launch {
//            notesRepository.createNote(note)
//        }
//    }
//
//
////    fun createEmptyNote(onNoteCreated: (Long) -> Unit) {
////        viewModelScope.launch {
////            val note = Note(userId = userAccountData.value.id, title = "", content = "")
////            val id = notesRepository.createNote(note)
////            val savedNote = note.copy(id = id)
////            _note.value = savedNote
////            _noteTitle.value = savedNote.title
////            _noteContent.value = savedNote.content
////            startAutoSave()
////            onNoteCreated(id)
////        }
////    }
//
//
//
////    fun createEmptyNote(onNoteCreated: (Long) -> Unit) {
////        viewModelScope.launch {
////            userAccountData
////                .filter { it.id != 0L }
////                .firstOrNull()?.let { userData ->
////                    val note = Note(userId = userData.id, title = "Untitled ${System.currentTimeMillis()}", content = "")
////                    val id = notesRepository.createNote(note)
////                    val savedNote = note.copy(id = id)
////                    _note.value = savedNote
////                    _noteTitle.value = savedNote.title
////                    _noteContent.value = savedNote.content
////                    startAutoSave()
////                    onNoteCreated(id)
////                } ?: Log.e("NotesHomeViewModel", "User ID is still 0. Cannot create note.")
////        }
////    }
//fun createEmptyNote(onNoteCreated: (Long) -> Unit) {
//    viewModelScope.launch {
//        val currentUserId = userAccountData.value.id
//        if (currentUserId == 0L) {
//            Log.e("NotesHomeViewModel", "User ID is 0. Cannot create note.")
//            return@launch
//        }
//        val note = Note(userId = currentUserId, title = "", content = "")
//        val id = notesRepository.createNote(note)
//        val savedNote = note.copy(id = id)
//
//        _note.value = savedNote             // ✅ 1. Set the note first
//        _noteTitle.value = savedNote.title  // ✅ 2. Then set the title/content
//        _noteContent.value = savedNote.content
//        startAutoSave()                     // ✅ 3. Only then start auto-save
//
//        onNoteCreated(id)
//    }
//}
//
//
//
//
//
//    fun deleteNote(note: Note) {
//        viewModelScope.launch {
//            notesRepository.delete(note)
//        }
//    }
//
//    fun editNote(note: Note) {
//        viewModelScope.launch {
//            notesRepository.edit(note)
//        }
//    }
//
//    fun clearSearchResult() {
//        _searchQuery.value = ""
//    }
//
//    fun updateLastOpenedNoteId(id: Long) {
//        viewModelScope.launch {
//            notesRepository.setLastOpenedNoteId(id)
//        }
//    }
//
//    fun clearLastOpenedNoteId() {
//        viewModelScope.launch {
//            notesRepository.clearLastOpenedNoteId()
//        }
//    }
//
//    fun clearCurrentNote(){
//        _note.value = null
//        _noteTitle.value = ""
//        _noteContent.value = ""
//    }
//}







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