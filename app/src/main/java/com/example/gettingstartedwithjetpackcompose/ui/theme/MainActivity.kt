package com.example.gettingstartedwithjetpackcompose.ui.theme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import com.example.gettingstartedwithjetpackcompose.R
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gettingstartedwithjetpackcompose.data.model.local.UserDao
import com.example.gettingstartedwithjetpackcompose.data.model.local.AppDatabase
import com.example.gettingstartedwithjetpackcompose.data.model.local.User
//import com.example.gettingstartedwithjetpackcompose.Routes.AppNavHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    const val HOME = "home screen"
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()){
    val context = LocalContext.current
    val userDao: UserDao = remember { AppDatabase.getDatabase(context).UserDao() }

    NavHost(
        navController  = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                userDao = userDao,
                onNavigateToHome = {navController.navigate(Routes.HOME)},
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                userDao = userDao,
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}

@Composable
fun LoginScreen(userDao: UserDao, onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //var userIcon = androidx.compose.ui.res.painterResource(id = R.drawable.user_icon)
    var showPassword by remember { mutableStateOf(false) }

    val context   = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()){
        Canvas(modifier = Modifier.fillMaxSize()){
            val width = size.width
            val height = size.height

            val path = Path().apply{
                moveTo(0f, 0f)
                lineTo(0f, height*0.6f)
                cubicTo(width*0.2f,height*0.35f, width*0.7f, height*0.65f, width, height*0.6f)
                lineTo(width, 0f)
                close()
            }
            drawPath(path, color = Color(0xFFDE91EA))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Card(shape = RoundedCornerShape(24.dp), modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White,
                    contentColor = Color.Black)) {

                Column(modifier = Modifier.padding(top=50.dp, bottom= 20.dp, start= 10.dp, end = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text("LOG IN", fontSize = 50.sp, fontFamily = Roboto)
                    //Text("to start your journey", fontFamily = Roboto, fontSize = 30.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Username icon")}


                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.MailOutline,
                                contentDescription = "Email icon")
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
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
                    FilledTonalButton(
                        modifier = Modifier.width(280.dp),
                        onClick = {scope.launch {
                            val user = withContext(Dispatchers.IO) {
                                userDao.login(email.trim(), password)
                            }
                            if (user != null)
                                onNavigateToHome()
                            else
                                Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                        } }
                    ) {Text("Log In")} }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                Text("Don't have an account?", fontFamily = Roboto, fontSize = 10.sp)
                TextButton(onClick = onNavigateToRegister) { Text("Register now", fontFamily = Roboto, fontSize = 10.sp)}
            }
        }


    }
}


@Composable
fun RegisterScreen(userDao: UserDao, onNavigateToLogin: () -> Unit){
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, height * 0.6f)
                cubicTo(width * 0.2f, height * 0.35f, width * 0.7f, height * 0.65f, width, height * 0.6f)
                lineTo(width, 0f)
                close()
            }
            drawPath(path, color = Color(0xFFDE91EA))
        }
    }
    Column {
        Card(
            shape = RoundedCornerShape(24.dp), modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp)
                .width(350.dp)
                .height(500.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black)
        ){
            Column(modifier = Modifier.padding(top=10.dp, bottom= 5.dp, start= 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally){
                var username by remember {mutableStateOf("")}
                var email by remember {mutableStateOf("")}
                var password by remember {mutableStateOf("")}
                var confirmPass by remember {mutableStateOf("")}
                //var userIcon = painterResource(id = R.drawable.user_icon)
                var showPassword by remember { mutableStateOf(false) }
                var confirmShowPassword by remember {mutableStateOf(false)}

                var context = LocalContext.current
                val scope = rememberCoroutineScope()

                Text("WELCOME" , fontFamily = Roboto, fontSize = 50.sp, modifier = Modifier.padding(top = 20.dp))
                //Text("Join us to start your journey", fontFamily = Roboto, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(15.dp))

                //Text("Enter your username", fontFamily = Roboto, fontSize = 15.sp)
                OutlinedTextField(
                    value = username,
                    onValueChange = {username = it},
                    label = { Text("Enter your username") },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Username icon"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))


                //Text("Enter your email", fontFamily = Roboto, fontSize = 15.sp)
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    label = {Text("Enter your email")},
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.MailOutline,
                            contentDescription = "Mail icon"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))


                //Text("Enter your password", fontFamily = Roboto, fontSize = 15.sp)
                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = {Text("Enter your password")},
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
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

                Spacer(modifier = Modifier.height(15.dp))

                //Text("Confirm your password", fontFamily = Roboto, fontSize = 15.sp)
                OutlinedTextField(
                    value = confirmPass,
                    onValueChange = {confirmPass = it},
                    label = {Text("Confirm password")},
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
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

                Spacer(modifier = Modifier.height(15.dp))
                FilledTonalButton(modifier = Modifier.width(280.dp),
                    onClick = {
                        if (password != confirmPass) {
                            Toast.makeText(context, "Passwords don’t match", Toast.LENGTH_SHORT).show()
                            return@FilledTonalButton
                        }

                        val newUser = User(username = username, email = email.trim(), passwordHash = password)
                        scope.launch {
                            val ok = withContext(Dispatchers.IO) {
                                runCatching { userDao.insertUser(newUser) }.isSuccess
                            }
                            if (ok) {
                                Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
                                onNavigateToLogin()
                            } else {
                                Toast.makeText(context, "Email already in use", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) { Text("Register") }

            }
        }
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Text("Already have an account?", fontFamily = Roboto, fontSize = 10.sp)
            TextButton(onClick = onNavigateToLogin) {Text("Log in", fontFamily = Roboto, fontSize = 10.sp)}
        }
    }
}


@Composable
fun HomeScreen(){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text("MAIN PAGE", fontSize = 150.sp)
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    GettingStartedWithJetpackComposeTheme {
//        Greeting("Android")
//    }
//}

