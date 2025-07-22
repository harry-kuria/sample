package com.example.gettingstartedwithjetpackcompose.ui.theme.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gettingstartedwithjetpackcompose.data.local.Note
import com.example.gettingstartedwithjetpackcompose.ui.theme.Roboto
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun NoteItem(note: Note, onNavigateToEditNote: (Long) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFEADDEE), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { onNavigateToEditNote(note.id) }
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun NotesHomeScreen(viewModel: NotesHomeViewModel = hiltViewModel(),
                    onNavigateToEditNote: (Long) -> Unit,
                    onNavigateToMyAccount: () -> Unit) {
    val notes by viewModel.notes.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var isSearching by remember {mutableStateOf(false)}
    val query by viewModel.searchQuery.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDE91EA),
                    titleContentColor = Color.Black,
                ),
                title = {
                    if (isSearching){
                        TextField(
                            value = query,
                            onValueChange = { viewModel.updateSearchQuery(it) },
                            placeholder = { Text("Look for a note...") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                        )
                    }else{
                        Text("Notes", maxLines = 1, overflow = TextOverflow.Ellipsis,
                            fontSize = 30.sp, fontFamily = Roboto)
                    }
                },
                actions = {
                    IconButton(onClick = { isSearching = !isSearching }) {
                        Icon(imageVector = Icons.Filled.Search,
                            contentDescription = "Look for a note",
                            modifier = Modifier.size(35.dp),
                                //.padding(end = 10.dp),
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { onNavigateToMyAccount() }) {
                        Icon(imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "My Account",
                            modifier = Modifier.size(35.dp),
                            tint = Color.Black
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.createEmptyNote { id -> onNavigateToEditNote(id)  }}, shape = CircleShape) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .pointerInput(isSearching) {
                    if (isSearching) {
                        detectTapGestures(onTap = {
                            isSearching = false
                        })
                    }
                }
                .padding(padding)
        ) {
            LazyColumn {
                items(notes) { note ->
                    NoteItem(note = note, onNavigateToEditNote = onNavigateToEditNote)
                }
            }
        }
    }
}