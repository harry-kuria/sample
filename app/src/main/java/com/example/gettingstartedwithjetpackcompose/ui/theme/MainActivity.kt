package com.example.gettingstartedwithjetpackcompose.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gettingstartedwithjetpackcompose.ui.theme.nav.AppNavHost
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GettingStartedWithJetpackComposeTheme {
                AppNavHost()


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

