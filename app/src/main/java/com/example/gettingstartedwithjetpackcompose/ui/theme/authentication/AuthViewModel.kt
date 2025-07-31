package com.example.gettingstartedwithjetpackcompose.ui.theme.authentication

import android.util.Log
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

    private val _isLoggedIn =  MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    val username = userAuthRepository.userData
        .map { it.username }
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), "")

//    private val _isSessionValid = MutableStateFlow<Boolean?>(null)
//    val isSessionValid: StateFlow<Boolean?> = _isSessionValid
//
//    fun checkSessionValid() = viewModelScope.launch {
//        val isValid = userAuthRepository.isSessionValid()
//        _isSessionValid.value = isValid
//    }


    init{
        //init is like a constructor. it is called automatically when the class is created
        //used here because we need the sessionReady to be flipped to true immediately the user is logged in so that home screen can pop up
        viewModelScope.launch {
            userAuthRepository.userData
                .onStart { _isSessionReady.value = false }
                .collect { account ->
                    _isSessionReady.value = true
                    _isLoggedIn.value = account.isLoggedIn == true
                }
        }
    }

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
        _loginState.update { it.copy(error = null) }

        val lState = _loginState.value
        if (lState.email.isEmpty() || lState.password.isEmpty()) {
            _loginState.update { it.copy(error = "Email and password are required") }
            return@launch
        }

        _loginState.update { it.copy(isLoading = true) }

        val emailExists = withContext(Dispatchers.IO) {
            userAuthRepository.getUserByEmail(lState.email) != null
        }

        if (!emailExists) {
            _loginState.update {
                it.copy(isLoading = false, isLoggedIn = false, error = "Email not found. Register instead")
            }
            return@launch
        }

        val result = withContext(Dispatchers.IO) {
            userAuthRepository.loginUser(lState.email, lState.password)
        }

        result.fold( //fold handles success and failure cases all at once: no if...else needed
            onSuccess = { user ->
                val fullUser = userAuthRepository.getUserByEmail(user.email)
                if (fullUser != null) {
                    userAuthRepository.saveUserAccountData(
                        id = fullUser.id,
                        email = fullUser.email,
                        username = fullUser.username
                    )
                    userAuthRepository.updateLoggedInStatus(fullUser.id, true)
                    Log.d("AuthViewModel", "Logged in with user ID: ${fullUser.id}")
                }

                _loginState.update {
                    it.copy(isLoading = false, isLoggedIn = true, error = null)
                }
            },
            onFailure = { error ->
                _loginState.update {
                    it.copy(isLoading = false, isLoggedIn = false, error = error.message ?: "Login failed")
                }
            }
        )
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

        val newUser =
            User(
                username = rState.username,
                email = rState.email,
                passwordHash = rState.password,
                isLoggedIn = true
            )

        val newUserId = withContext(Dispatchers.IO) {
            //runCatching {
                userAuthRepository.registerUser(newUser)
            //}.getOrNull()
        }

        val ok = newUserId!! > 0 //!= null

//        val ok = withContext(Dispatchers.IO) {
//            runCatching { userAuthRepository.registerUser(newUser) }.isSuccess
//        }

        _registerState.update {
            it.copy(
                isLoading = false, isRegistered = ok,
                error = if (!ok) "Registration failed" else null
            )
        }

//        if (ok) {
//            userAuthRepository.saveUserAccountData(id = newUser.id, email = rState.email, username = rState.username)
//            _isSessionReady.value = true
//        }
        if (ok && newUserId != null) {
            userAuthRepository.saveUserAccountData(
                id = newUserId,
                email = rState.email,
                username = rState.username
            )
            _isSessionReady.value = true
            Log.d("AuthViewModel", "User registered with ID: $newUserId")
        }


    }
    fun clearRegisterSuccess() = _registerState.update { it.copy(isRegistered = false) }
}