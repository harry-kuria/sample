package com.example.gettingstartedwithjetpackcompose.ui.theme.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gettingstartedwithjetpackcompose.ui.theme.Roboto

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(onNavigateToMyAccount: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDE91EA),
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text("Dashboard", maxLines = 1, overflow = TextOverflow.Ellipsis,
                        fontSize = 30.sp, fontFamily = Roboto)
                },
                actions = {
                    IconButton(onClick = { onNavigateToMyAccount() }) {
                        Icon(imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "My Account",
                            modifier = Modifier.size(35.dp),
                                //.padding(end = 10.dp),
                            tint = Color.Black
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding -> Column(
        modifier = Modifier
            .padding(innerPadding) // <- THIS is where innerPadding is used
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Text("MAIN PAGE", fontSize = 40.sp, fontWeight = FontWeight.Bold)
    }

    }
}

