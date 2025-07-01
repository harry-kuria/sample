package com.example.gettingstartedwithjetpackcompose

import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gettingstartedwithjetpackcompose.ui.theme.GettingStartedWithJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GettingStartedWithJetpackComposeTheme {
                LoginScreen()
                RegisterScreen()
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
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //should the password have a mutableState saving thing. it shouldn't be saved so the user has security


    Column(modifier = Modifier.padding(top=20.dp, bottom= 20.dp, start= 10.dp, end = 10.dp)) {
        Text("LOG IN", fontSize = 30.sp)
        Text("to start your journey...")


        Text("Enter your username")
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Enter your email address")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text("Enter your password")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
            //find out how to change the type to password so it has dots
        )

        Spacer(modifier = Modifier.height(20.dp))
        //FilledTonalButton(onClick = when clicked should take you to the main page or toast that the login was successful) { }

        Row {
            Text("Don't have an account?")
            //TextButton(onClick = when clicked should take you to the log in page) { }
        }
    }


}


@Composable
fun RegisterScreen(){
    Column {
        var username by remember {mutableStateOf("")}
        var email by remember {mutableStateOf("")}
        var password by remember {mutableStateOf("")}
        var confirmPass by remember {mutableStateOf("")}

        Text("WELCOME")
        Text("Join us to start your learning journey")

        Text("Enter your username")
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            label = { Text("Username") }
        )
        
        Text("Enter your email")
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Email")}
        )

        Text("Enter your password")
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")}
        )

        Text("Confirm your password")
        OutlinedTextField(
            value = confirmPass,
            onValueChange = {confirmPass = it},
            label = {Text("Confirm Password")}
        )

        Spacer(modifier = Modifier.height(20.dp))
        //FilledTonalButton(onClick = when clicked should take you to the main page or toast that the log in was succesful) { }

        Row {
            Text("Already have an account?")
            //TextButton(onClick = when clicked should take you to the log in page) { }

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