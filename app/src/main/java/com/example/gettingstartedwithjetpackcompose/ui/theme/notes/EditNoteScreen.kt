package com.example.gettingstartedwithjetpackcompose.ui.theme.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@Composable
fun EditNoteScreen(noteId: Long,
                   viewModel: NotesHomeViewModel = hiltViewModel(), onNavigateBack: () -> Unit) {

    val title by viewModel.noteTitle.collectAsState()
    val content by viewModel.noteContent.collectAsState()

    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onNavigateBack) {
            (Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back"))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = title,
            onValueChange = { viewModel.updateTitle(it) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = content,
            onValueChange = { viewModel.updateContent(it) },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxLines = Int.MAX_VALUE
        )
    }
}