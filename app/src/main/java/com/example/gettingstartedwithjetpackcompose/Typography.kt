package com.example.gettingstartedwithjetpackcompose

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.gettingstartedwithjetpackcompose.ui.theme.Typography

val Roboto = FontFamily(
    Font(R.font.roboto_condensed_light, FontWeight.Light)
)

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp
    )

)