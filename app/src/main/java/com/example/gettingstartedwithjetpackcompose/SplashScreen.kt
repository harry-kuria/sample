package com.example.gettingstartedwithjetpackcompose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.AuthViewModel
import androidx.compose.runtime.collectAsState
import com.example.gettingstartedwithjetpackcompose.ui.theme.navigation.Routes
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController, authVm: AuthViewModel = hiltViewModel()) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val progress by animateLottieCompositionAsState(composition, iterations = 12, speed = 1f)


    val loggedIn by authVm.isLoggedIn.collectAsState(initial = false)
    val sessionReady by authVm.isSessionReady.collectAsState()

    LaunchedEffect(progress, sessionReady) {
        if (progress == 1f && sessionReady) {
            //delay(5000)
            if (loggedIn) {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            } else {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize() .background(Color.White), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }

    Log.d("Compose", "Session ready in Compose: $sessionReady")


}
