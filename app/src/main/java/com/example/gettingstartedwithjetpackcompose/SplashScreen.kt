package com.example.gettingstartedwithjetpackcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.AuthViewModel
import androidx.compose.runtime.collectAsState
import com.example.gettingstartedwithjetpackcompose.ui.theme.navigation.Routes
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SplashScreen(navController: NavController, authVm: AuthViewModel = hiltViewModel()) {

    val loggedIn by authVm.isLoggedIn.collectAsState(initial = false)
    LaunchedEffect(Unit) {
        delay(5000)
        if (loggedIn){
            navController.navigate(Routes.HOME){
                popUpTo(Routes.SPLASH){inclusive = true}
            }
        }else{
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.SPLASH) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize() .background(Color.White), contentAlignment = Alignment.Center) {
        Image(painterResource(id = R.drawable.generic_logo), contentDescription = "Logo",
            modifier = Modifier.size(200.dp))
    }

}