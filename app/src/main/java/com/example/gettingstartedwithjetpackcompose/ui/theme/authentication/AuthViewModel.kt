package com.example.gettingstartedwithjetpackcompose.ui.theme.authentication

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.local.User
import com.example.gettingstartedwithjetpackcompose.data.repository.UserAuthRepository
import com.example.gettingstartedwithjetpackcompose.ui.theme.uiStates.LoginUiState
import com.example.gettingstartedwithjetpackcompose.ui.theme.uiStates.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userAuthRepository: UserAuthRepository): ViewModel() {


    private val _isSessionReady = MutableStateFlow(false)
    val isSessionReady: StateFlow<Boolean> = _isSessionReady.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = userAuthRepository.userData
        .onStart { _isSessionReady.value = false }
        .map { account ->
            _isSessionReady.value = true
            account.isLoggedIn == true}
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), false)


    val username = userAuthRepository.userData
        .map { it.username }
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), "")

//        val userEmail = userAuthRepository.userData
//            .map { it.email }
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")



    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    fun onLoginUsernameChange(u: String) = _loginState.update { it.copy(username = u.trim(), error = null) }
    fun onLoginEmailChange(e: String) = _loginState.update { it.copy(email = e.trim(), error = null) }
    fun onLoginPasswordChange(p: String) = _loginState.update { it.copy(password = p.trim(), error = null) }

    fun login() = viewModelScope.launch {
        _loginState.update {it.copy(error = null)}
        val lState = _loginState.value
        if (lState.email.isEmpty() || lState.password.isEmpty()) {
            _loginState.update { it.copy(error = "Email and password are required") }
            return@launch
        }

        _loginState.update { it.copy(isLoading = true) }

        val emailExists = withContext(Dispatchers.IO) {
            userAuthRepository.getUserByEmail(lState.email) != null
        }

        if (! emailExists) {
            _loginState.update { it.copy(isLoading = false, isLoggedIn = false, error = "Email not found. Register instead") }
            return@launch
        }

        val user = withContext(Dispatchers.IO) {
            userAuthRepository.loginUser(lState.email, lState.password)
        }

        val success = user != null

        _loginState.update {
            it.copy(
                isLoading = false, isLoggedIn = success,
                error = if (!success) "Incorrect email or password" else null
            )
        }

        if (success) {
            userAuthRepository.saveUserAccountData(email = lState.email, username = user.username)
        }
    }

    fun clearLoginSuccess() = _loginState.update { it.copy(isLoggedIn = false) }

    fun onRegisterEmailChange(e: String) = _registerState.update { it.copy(email = e.trim(), error = null) }
    fun onRegisterUsernameChange(u: String) = _registerState.update { it.copy(username = u.trim(), error = null) }
    fun onRegisterPasswordChange(p: String) = _registerState.update { it.copy(password = p.trim(), error = null) }
    fun onRegisterConfirmPasswordChange(p: String) = _registerState.update { it.copy(confirmPassword = p.trim(), error = null) }


    fun register() = viewModelScope.launch {
        _registerState.update {it.copy(error = null)}
        val rState = _registerState.value
        if (rState.email.isEmpty() || rState.username.isEmpty() || rState.password.isEmpty() || rState.confirmPassword.isEmpty()) {
            _registerState.update { it.copy(error = "All fields are required") }
            return@launch
        }
        //_registerState.update { it.copy(isLoading = true) }

        val emailExists = withContext(Dispatchers.IO) {
            userAuthRepository.getUserByEmail(rState.email) != null
        }
//        Flow<User?>

        if (! Patterns.EMAIL_ADDRESS.matcher(rState.email).matches()){
            _registerState.update { it.copy(error = "Invalid email format") }
            return@launch
        }

        if (emailExists) {
            _registerState.update {
                it.copy(error = "An account with this email already exists. Log in instead")
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

        val passValidate = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$".toRegex()
        if (! passValidate.matches(rState.password)){
            _registerState.update { it.copy(error = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one number", isLoading = false) }
            return@launch
        }

        val newUser =
            User(
                username = rState.username,
                email = rState.email,
                passwordHash = rState.password,
                isLoggedIn = true
            )


        val ok = withContext(Dispatchers.IO) {
            runCatching { userAuthRepository.registerUser(newUser) }.isSuccess
        }
        _registerState.update {
            it.copy(
                isLoading = false, isRegistered = ok,
                error = if (!ok) "Registration failed" else null
            )
        }

        if (ok) {
            userAuthRepository.saveUserAccountData(email = rState.email, username = rState.username)
            _isSessionReady.value = true
        }

    }
    fun clearRegisterSuccess() = _registerState.update { it.copy(isRegistered = false) }
}