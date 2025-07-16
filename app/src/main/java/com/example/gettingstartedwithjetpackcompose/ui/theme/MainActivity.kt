package com.example.gettingstartedwithjetpackcompose.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.AuthViewModel
import com.example.gettingstartedwithjetpackcompose.ui.theme.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authVm: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { //keep splash screen on until auth is ready
            !authVm.isSessionReady.value
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GettingStartedWithJetpackComposeTheme {
                val loggedIn by authVm.isLoggedIn.collectAsState(initial = false)
                if (authVm.isSessionReady.collectAsState().value) {
                    AppNavHost(isLoggedIn = loggedIn)
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    GettingStartedWithJetpackComposeTheme {
//        Greeting("Android")
//    }
//}

