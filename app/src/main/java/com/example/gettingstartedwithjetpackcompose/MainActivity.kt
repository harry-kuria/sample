package com.example.gettingstartedwithjetpackcompose

import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.gettingstartedwithjetpackcompose.Routes.AppNavHost
import com.example.gettingstartedwithjetpackcompose.ui.theme.GettingStartedWithJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GettingStartedWithJetpackComposeTheme {
              //Scaffold(modifier = Modifier.fillMaxSize())
                AppNavHost()

            }
        }
    }
}

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController  = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() }  // or navigate(LOGIN)
            )
        }
    }
}

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //should the password be saveable?. Less security
    var userIcon = painterResource(id = R.drawable.user_icon)


    Column(modifier = Modifier.padding(top=20.dp, bottom= 20.dp, start= 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = userIcon,
            contentDescription = "User icon", modifier = Modifier.size(100.dp))

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
            TextButton(onClick = onNavigateToRegister) { Text("Register now")}
        }
    }


}


@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit){
    Column(modifier = Modifier.padding(top=20.dp, bottom= 20.dp, start= 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        var username by remember {mutableStateOf("")}
        var email by remember {mutableStateOf("")}
        var password by remember {mutableStateOf("")}
        var confirmPass by remember {mutableStateOf("")}
        var userIcon = painterResource(id = R.drawable.user_icon)

        Image(painter = userIcon,
            contentDescription = "User icon", modifier = Modifier.size(100.dp))

        Text("WELCOME")
        Text("Join us to start your learning journey")

        Text("Enter your username")
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(20.dp))


        Text("Enter your email")
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Email")}
        )
        Spacer(modifier = Modifier.height(20.dp))


        Text("Enter your password")
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")}
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Confirm your password")
        OutlinedTextField(
            value = confirmPass,
            onValueChange = {confirmPass = it},
            label = {Text("Confirm Password")}
        )

        Spacer(modifier = Modifier.height(20.dp))
        //FilledTonalButton(onClick = when clicked should take you to the main page or toast that the log in was successful) { }

        Row {
            Text("Already have an account?")
            TextButton(onClick = onNavigateToLogin) {Text("Log in")}
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