package com.example.gettingstartedwithjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.gettingstartedwithjetpackcompose.ui.theme.GettingStartedWithJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GettingStartedWithJetpackComposeTheme {
                LoginScreen()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
//                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    // 🔹 'email' is a state variable that holds the input text
    // 🔹 'remember' keeps this value during recompositions
    var email by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("LOG IN", fontSize = 30.sp)
        Text("to start your journey...")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter your username")

        OutlinedTextField(
            // ✅ Required parameter: what text should be shown inside the text field
            value = email,

            // ✅ Called every time the user types — updates 'email'
            onValueChange = { email = it },

            // ✅ This is the label (hint) shown inside the text field
            label = { Text("Email") },

            // ✅ Makes the field fill the full width of the screen
            modifier = Modifier.fillMaxWidth()
        )
    }
}





//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    GettingStartedWithJetpackComposeTheme {
//        Greeting("Android")
//    }
//}
