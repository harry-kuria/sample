package com.example.gettingstartedwithjetpackcompose.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.local.UserDao
import com.example.gettingstartedwithjetpackcompose.data.local.User
import com.example.gettingstartedwithjetpackcompose.ui.theme.uiStates.LoginUiState
import com.example.gettingstartedwithjetpackcompose.ui.theme.uiStates.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject //or jakarta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel
class AuthViewModel @Inject constructor(private val userDao: UserDao): ViewModel() {
    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    fun onLoginUsernameChange(u: String) = _loginState.update { it.copy(username = u.trim(), error = null) }
    fun onLoginEmailChange(e: String) = _loginState.update { it.copy(email = e.trim(), error = null) }
    fun onLoginPasswordChange(p: String) = _loginState.update { it.copy(password = p.trim(), error = null) }

    fun login() = viewModelScope.launch {
        val lState = _loginState.value
        if (lState.email.isEmpty() || lState.password.isEmpty()) {
            _loginState.update { it.copy(error = "Email and password are required") }
            return@launch
        }

        _loginState.update { it.copy(isLoading = true) }

        val user = withContext(Dispatchers.IO) {
            userDao.login(lState.email, lState.password)
        }
        _loginState.update {
            it.copy(
                isLoading = false, isLoggedIn = user != null,
                error = if (user == null) "Incorrect email or password" else null
            )
        }
    }

    fun clearLoginSuccess() = _loginState.update { it.copy(isLoggedIn = false) }

    fun onRegisterEmailChange(e: String) = _registerState.update { it.copy(email = e.trim(), error = null) }
    fun onRegisterUsernameChange(u: String) = _registerState.update { it.copy(username = u.trim(), error = null) }
    fun onRegisterPasswordChange(p: String) = _registerState.update { it.copy(password = p.trim(), error = null) }
    fun onRegisterConfirmPasswordChange(p: String) = _registerState.update { it.copy(confirmPassword = p.trim(), error = null) }


    fun register() = viewModelScope.launch {
        val rState = _registerState.value
        if (rState.email.isEmpty() || rState.username.isEmpty() || rState.password.isEmpty() || rState.confirmPassword.isEmpty()) {
            _registerState.update { it.copy(error = "All fields are required") }
            return@launch
        }
        _registerState.update { it.copy(isLoading = true) }

        val emailExists = withContext(Dispatchers.IO) {
            userDao.findByEmail(rState.email) != null
        }

        if (emailExists) {
            _registerState.update {
                it.copy(isLoading = false, error = "An account with this email already exists. Log in instead")
            }
            return@launch
        }
        else{
            if (rState.password != rState.confirmPassword) {
                _registerState.update {
                    it.copy(isLoading = false, error = "Passwords don’t match")
                }
                return@launch
            }
        }


        val ok = withContext(Dispatchers.IO) {
            runCatching { userDao.insertUser(User(username = rState.username, email = rState.email.trim(),
                passwordHash = rState.password))}.isSuccess
        }
        _registerState.update {
            it.copy(
                isLoading = false, isRegistered = ok,
                error = if (ok) null else "Registration failed"
            )
        }

    }
    fun clearRegisterSuccess() = _registerState.update { it.copy(isRegistered = false) }
}