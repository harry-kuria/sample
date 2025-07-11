package com.example.gettingstartedwithjetpackcompose.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.repository.UserSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveLoginViewModel @Inject constructor(
    private val userSessionRepository: UserSessionRepository): ViewModel(){

        val isLoggedIn = userSessionRepository.userData
            .map { it.isLoggedIn }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

        val userEmail = userSessionRepository.userData
            .map { it.email }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

        val username = userSessionRepository.userData
            .map { it.username }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

        fun login(email: String, username: String){
            viewModelScope.launch { userSessionRepository.login(email = email, username = username)}
        }

        fun logout() {
            viewModelScope.launch { userSessionRepository.logout() }
        }
    }
