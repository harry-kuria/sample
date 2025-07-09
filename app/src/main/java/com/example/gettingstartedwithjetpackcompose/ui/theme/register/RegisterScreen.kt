package com.example.gettingstartedwithjetpackcompose.ui.theme.register

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gettingstartedwithjetpackcompose.R
import com.example.gettingstartedwithjetpackcompose.ui.theme.Roboto
import com.example.gettingstartedwithjetpackcompose.ui.theme.AuthViewModel



@Composable
//hint: declare the viewmodel as a constructor
fun RegisterScreen(viewModel: AuthViewModel= hiltViewModel(),
                   onNavigateToLogin: () -> Unit, onNavigateToHome: () -> Unit) {

//fun RegisterScreen(userDao: UserDao, viewModel: AuthViewModel= viewModel(), onNavigateToLogin: () -> Unit,
//                   onNavigateToHome: () -> Unit)

    // do away with view model factories
    val state by viewModel.registerState.collectAsState()

    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            onNavigateToHome()
            viewModel.clearRegisterSuccess()
        }
    }
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
    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = RoundedCornerShape(24.dp), modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp)
                .width(350.dp)
                .height(530.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black)
        ){
            Column(modifier = Modifier.padding(top=10.dp, bottom= 0.dp, start= 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally){
                var showPassword by remember { mutableStateOf(false) }
                var confirmShowPassword by remember {mutableStateOf(false)}

                Text("WELCOME" , fontFamily = Roboto, fontSize = 50.sp, modifier = Modifier.padding(top = 20.dp))
                //Text("Join us to start your journey", fontFamily = Roboto, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(15.dp))

                //Text("Enter your username", fontFamily = Roboto, fontSize = 15.sp)
                OutlinedTextField(
                    value = state.username,
                    onValueChange = viewModel::onRegisterUsernameChange,
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
                    value = state.email,
                    onValueChange = viewModel::onRegisterEmailChange,
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
                    value = state.password,
                    onValueChange = viewModel::onRegisterPasswordChange,
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
                    value = state.confirmPassword,
                    onValueChange = viewModel::onRegisterConfirmPasswordChange,
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

                val errorMsg = state.error
                if (errorMsg != null) {
                    Text(
                        text = errorMsg,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }


                FilledTonalButton(modifier = Modifier.width(280.dp),
                    onClick = viewModel::register, enabled = !state.isLoading){
                    if (state.isLoading) CircularProgressIndicator(Modifier.size(20.dp))
                    else Text("Register", fontFamily = Roboto, fontSize = 15.sp)
                }

            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Text("Already have an account?", fontFamily = Roboto, fontSize = 10.sp)
            TextButton(onClick = onNavigateToLogin) {Text("Log in", fontFamily = Roboto, fontSize = 10.sp)}
        }
    }
}
