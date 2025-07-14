package com.example.gettingstartedwithjetpackcompose.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gettingstartedwithjetpackcompose.ui.theme.nav.AppNavHost
import com.example.gettingstartedwithjetpackcompose.ui.theme.viewModel.SaveLoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GettingStartedWithJetpackComposeTheme {
                val saveLoginVm: SaveLoginViewModel = hiltViewModel()
                val loggedIn by saveLoginVm.isLoggedIn.collectAsState(initial = false)
                AppNavHost(isLoggedIn = loggedIn)


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

