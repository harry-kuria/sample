package com.example.gettingstartedwithjetpackcompose.ui.theme

import androidx.navigation.compose.rememberNavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.gettingstartedwithjetpackcompose.features.authentication.viewModel.AuthViewModel
import com.example.gettingstartedwithjetpackcompose.features.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authVm: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !authVm.isSessionReady.value
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sessionReady by authVm.isSessionReady.collectAsState()
            val loggedIn by authVm.isLoggedIn.collectAsState(initial = false)
            val navController = rememberNavController()

            GettingStartedWithJetpackComposeTheme {
                if (sessionReady) {
                    AppNavHost(
                        navController = navController,
                        isLoggedIn = loggedIn,
                    )


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