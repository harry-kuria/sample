package com.example.gettingstartedwithjetpackcompose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
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
import com.example.gettingstartedwithjetpackcompose.ui.theme.notes.NotesHomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map


@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
//@Composable
//fun SplashScreen(navController: NavController, authVm: AuthViewModel = hiltViewModel(),
//                 notesVm: NotesHomeViewModel = hiltViewModel()) {
//
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
//    val progress by animateLottieCompositionAsState(composition, speed = 1f)
//
//    val loggedIn by authVm.isLoggedIn.collectAsState(initial = false)
//    val sessionReady by authVm.isSessionReady.collectAsState()
//
//    val notesSession by notesVm.userAccountData.collectAsState()
//
//    LaunchedEffect(progress, sessionReady, loggedIn, notesSession) {
//        delay(500)
//        if (sessionReady) {
//            if (loggedIn) {
//                val lastOpenedNoteId = notesSession.lastOpenedNoteId
//                if (lastOpenedNoteId > 0L) {
//                    navController.navigate("${Routes.EDIT_NOTE}/$lastOpenedNoteId") {
//                        popUpTo(Routes.SPLASH) { inclusive = true }
//                    }
//                } else {
//                    navController.navigate(Routes.HOME) {
//                        popUpTo(Routes.SPLASH) { inclusive = true }
//                    }
//                }
//            } else {
//                navController.navigate(Routes.LOGIN) {
//                    popUpTo(Routes.SPLASH) { inclusive = true }
//                }
//            }
//        }
//    }
//
//    Box(
//        modifier = Modifier.fillMaxSize().background(Color.White),
//        contentAlignment = Alignment.Center
//    ) {
//        LottieAnimation(
//            composition = composition,
//            progress = { progress }
//        )
//    }
//}









@Composable
fun SplashScreen(
    navController: NavController,
    authVm: AuthViewModel = hiltViewModel(),
    notesVm: NotesHomeViewModel = hiltViewModel()
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val progress by animateLottieCompositionAsState(composition, speed = 1f)

    val loggedIn by authVm.isLoggedIn.collectAsState(initial = false)
    val sessionReady by authVm.isSessionReady.collectAsState()

    val currentUserId by notesVm.currentUserId.collectAsState()

    LaunchedEffect(progress, sessionReady, loggedIn, currentUserId) {
        if (!sessionReady) return@LaunchedEffect

        delay(500) // Minimum display time

        val destination = when {
            !loggedIn -> Routes.LOGIN
            currentUserId > 0L -> Routes.HOME // Or your default destination
            else -> Routes.LOGIN // Fallback
        }

        navController.navigate(destination) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), //(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }
}