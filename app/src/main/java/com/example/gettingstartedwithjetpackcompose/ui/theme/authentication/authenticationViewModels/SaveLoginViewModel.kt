

package com.example.gettingstartedwithjetpackcompose.ui.theme.authentication.authenticationViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.repository.UserDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveLoginViewModel @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository): ViewModel(){

        val isLoggedIn = userDataStoreRepository.userData
            .map { it.isLoggedIn }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

//        val userEmail = userDataStoreRepository.userData
//            .map { it.email }
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

        val username = userDataStoreRepository.userData
            .map { it.username }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

        fun login(email: String, username: String){
            viewModelScope.launch { userDataStoreRepository.saveUserAccountData(email = email, username = username)}
        }

        fun logout() {
            viewModelScope.launch { userDataStoreRepository.clearUserAccountData() }
        }
    }
