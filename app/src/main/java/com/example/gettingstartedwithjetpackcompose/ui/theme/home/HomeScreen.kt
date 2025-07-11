package com.example.gettingstartedwithjetpackcompose.ui.theme.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text("MAIN PAGE", fontSize = 40.sp)

    }
}