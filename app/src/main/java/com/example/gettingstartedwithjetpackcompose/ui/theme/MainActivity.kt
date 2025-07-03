package com.example.gettingstartedwithjetpackcompose.ui.theme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gettingstartedwithjetpackcompose.R
import com.example.gettingstartedwithjetpackcompose.Roboto
import com.example.gettingstartedwithjetpackcompose.data.model.local.AppDatabase
import com.example.gettingstartedwithjetpackcompose.data.model.local.User
import com.example.gettingstartedwithjetpackcompose.data.model.local.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//import com.example.gettingstartedwithjetpackcompose.Routes.AppNavHost


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
    const val REGISTER = "register"
    const val HOMESCREEN = "home screen"
}

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
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onNavigateToHomeScreen = {navController.navigate(Routes.HOMESCREEN)}
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) }  // or navigate(LOGIN)
            )
        }

        composable(Routes.HOMESCREEN){
            HomeScreen(onNavigateToHomeScreen = {navController.navigate(Routes.HOMESCREEN)})

        }
    }
}


@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onNavigateToHomeScreen: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userIcon = painterResource(id = R.drawable.user_icon)
    var showPassword by remember { mutableStateOf(false) }

    val scope   = rememberCoroutineScope()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.UserDao()

    ///Wrapped the login screen in a box to center it
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = userIcon, contentDescription = "User icon",
                modifier = Modifier.size(100.dp), alignment = Alignment.Center)

            Text("LOG IN", fontSize = 50.sp, fontFamily = Roboto)
            Text("to start your journey...", fontFamily = Roboto, fontSize = 30.sp)
            Spacer(modifier = Modifier.height(20.dp))



            Text("Enter your username", fontFamily = Roboto, fontSize = 20.sp)
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Username icon")}

            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Enter your email address", fontFamily = Roboto, fontSize = 20.sp)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.MailOutline,
                        contentDescription = "Email icon")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text("Enter your password", fontFamily = Roboto, fontSize = 20.sp)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password icon"
                    )},
                trailingIcon = { var visibilityIcon = if (showPassword) R.drawable.visibility_on else R.drawable.visibility_off
                    IconButton(onClick = {showPassword = !showPassword}) {
                        Icon(painter = painterResource(visibilityIcon),
                            contentDescription = if (showPassword) "show password" else "hide password",
                            modifier = Modifier.size(20.dp))}

                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            FilledTonalButton(onClick = {scope.launch{
                val user = withContext(Dispatchers.IO){userDao.login(email.trim(), password)}
                if (user != null) {
                    onNavigateToHomeScreen()
                } else {
                    Toast.makeText(context, "Incorrect password or email. Try again", Toast.LENGTH_SHORT).show()
                }
            }}){Text("Log in")}

            Row{
                Text("Don't have an account?", fontFamily = Roboto, fontSize = 10.sp)
                TextButton(onClick = onNavigateToRegister) { Text("Register now", fontFamily = Roboto, fontSize = 10.sp)}
            }


        }
    }
}

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit){
    Column(modifier = Modifier.padding(top=10.dp, bottom= 20.dp, start= 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        var username by remember {mutableStateOf("")}
        var email by remember {mutableStateOf("")}
        var password by remember {mutableStateOf("")}
        var confirmPass by remember {mutableStateOf("")}
        var userIcon = painterResource(id = R.drawable.user_icon)
        var showPassword by remember { mutableStateOf(false) }
        var confirmShowPassword by remember {mutableStateOf(false)}

        val context = LocalContext.current
        val db = AppDatabase.getDatabase(context)
        val userDao = db.UserDao()
        val newUser = User(username = username, email = email, passwordHash = password)


        Image(painter = userIcon,
            contentDescription = "User icon", modifier = Modifier.size(100.dp),
            alignment = Alignment.Center)

        Text("WELCOME" , fontFamily = Roboto, fontSize = 45.sp)
        Text("Join us to start your journey", fontFamily = Roboto, fontSize = 25.sp)
        Spacer(modifier = Modifier.height(20.dp))

        Text("Enter your username", fontFamily = Roboto, fontSize = 20.sp)
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            label = { Text("Username") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Username icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))


        Text("Enter your email", fontFamily = Roboto, fontSize = 20.sp)
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Email")},
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = "Mail icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))


        Text("Enter your password", fontFamily = Roboto, fontSize = 20.sp)
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")},
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password icon")
            },
            trailingIcon = { var visibilityIcon = if (showPassword) R.drawable.visibility_on else R.drawable.visibility_off
                IconButton(onClick = {showPassword = !showPassword}) {
                    Icon(painter = painterResource(visibilityIcon),
                        contentDescription = if (showPassword) "show password" else "hide password",
                        modifier = Modifier.size(20.dp))}

            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Confirm your password", fontFamily = Roboto, fontSize = 20.sp)
        OutlinedTextField(
            value = confirmPass,
            onValueChange = {confirmPass = it},
            label = {Text("Confirm Password")},
            singleLine = true,
            visualTransformation = if (confirmShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Password icon"
                )
            },
            trailingIcon = { var visibilityIcon = if (confirmShowPassword) R.drawable.visibility_on else R.drawable.visibility_off
                IconButton(onClick = {confirmShowPassword = !confirmShowPassword}) {
                    Icon(painter = painterResource(visibilityIcon),
                        contentDescription = if (confirmShowPassword) "show password" else "hide password",
                        modifier = Modifier.size(20.dp))}

            }

        )



        Spacer(modifier = Modifier.height(20.dp))
        //FilledTonalButton(onClick = when clicked should take you to the main page or toast that the log in was successful) { }

        Row {
            Text("Already have an account?", fontFamily = Roboto, fontSize = 10.sp)
            TextButton(onClick = onNavigateToLogin) {Text("Log in", fontFamily = Roboto, fontSize = 10.sp)}
        }
        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.insertUser(newUser)
            }
    }
    }
}

@Composable
fun HomeScreen(onNavigateToHomeScreen: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("MAIN SCREEN", fontSize = 150.sp)
    }
}



//added preview that shows the login screen
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
    }
}