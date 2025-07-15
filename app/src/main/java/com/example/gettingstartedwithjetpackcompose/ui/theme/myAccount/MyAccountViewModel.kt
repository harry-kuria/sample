package com.example.gettingstartedwithjetpackcompose.ui.theme.myAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.repository.UserDataStoreRepository
import com.example.gettingstartedwithjetpackcompose.ui.theme.uiStates.MyAccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(private val userDataStoreRepository: UserDataStoreRepository)
    : ViewModel() {
        val myAccountState: StateFlow<MyAccountUiState> = userDataStoreRepository.userData.map{ sessionData ->
            MyAccountUiState(username = sessionData.username, email = sessionData.email) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyAccountUiState())

        private val _myAccountUiEvent = MutableSharedFlow<MyAccountUiEvent>()
        val myAccountUiEvent: SharedFlow<MyAccountUiEvent> = _myAccountUiEvent

        fun onEvent(event: MyAccountEvent) {
            viewModelScope.launch {
                when (event) {
                    MyAccountEvent.SettingsClicked -> _myAccountUiEvent.emit(MyAccountUiEvent.NavigateToSettings)
                    MyAccountEvent.NotificationsClicked -> _myAccountUiEvent.emit(MyAccountUiEvent.NavigateToNotifications)
                    MyAccountEvent.FAQClicked -> _myAccountUiEvent.emit(MyAccountUiEvent.NavigateToFAQ)
                    MyAccountEvent.AboutClicked -> _myAccountUiEvent.emit(MyAccountUiEvent.NavigateToAbout)

                    MyAccountEvent.LogoutClicked -> {
                        userDataStoreRepository.clearUserAccountData()
                        _myAccountUiEvent.emit(MyAccountUiEvent.NavigateToLogin)
                    }

                    MyAccountEvent.BackClicked -> _myAccountUiEvent.emit(MyAccountUiEvent.Back)
                }
            }
        }
}