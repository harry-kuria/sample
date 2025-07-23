package com.example.gettingstartedwithjetpackcompose.ui.theme.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gettingstartedwithjetpackcompose.data.local.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun NoteItem(note: Note, onNavigateToEditNote: (Long) -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
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

@Composable
fun SearchBar( query: String, onQueryChange: (String) -> Unit, onSearch: () -> Unit) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.LightGray, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.padding(end = 8.dp)
            )

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onSearch()
                    }
                ),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Looking for a note",
                            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun NotesHomeScreen(viewModel: NotesHomeViewModel = hiltViewModel(),
                    onNavigateToEditNote: (Long) -> Unit,
                    onNavigateToMyAccount: () -> Unit){

    val allNotes by viewModel.notes.collectAsState()
    //val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val query by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDE91EA),
                    titleContentColor = Color.Black,
                ),
                actions = {
                    IconButton(onClick = { onNavigateToMyAccount() }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "My Account",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            )
        },
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.createEmptyNote { newNoteId -> onNavigateToEditNote(newNoteId) } },
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
            .pointerInput(Unit){detectTapGestures(onTap = { focusManager.clearFocus() })}
            .background(Color.White)
        ){
            Column(modifier = Modifier.padding(3.dp)
            ) {
                SearchBar(query = query,
                    onQueryChange = { viewModel.updateSearchQuery(it) } ,
                    onSearch = { focusManager.clearFocus() }
                )

                //Spacer(modifier = Modifier.height(10.dp))

                val notesToShow = if (query.isBlank()) allNotes else searchResults

                if (notesToShow.isEmpty()) {
                    Text("No notes found", style = MaterialTheme.typography.bodyLarge)
                } else {
                    LazyColumn {
                        items(notesToShow, key = { it.id }) { note ->
                            val dismissState = rememberDismissState(confirmStateChange = {
                                if (it == DismissValue.DismissedToEnd) {
                                    viewModel.deleteNote(note)
                                    true
                                } else{false}
                            }
                            )

                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(DismissDirection.StartToEnd),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.Transparent),
                                background = {
                                    val swipeOffset = dismissState.offset.value.coerceAtLeast(0f)

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(with(LocalDensity.current) { swipeOffset.toDp() })
                                                .fillMaxHeight()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color.Red)
                                                .padding(start = 16.dp),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete note",
                                                tint = Color.Black,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                },
                                dismissContent = {
                                    NoteItem( note = note,
                                        onNavigateToEditNote = { onNavigateToEditNote(note.id) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp, vertical = 4.dp)

                                    )
                                }
                            )

                        }

                    }
                }
            }
        }
    }
}