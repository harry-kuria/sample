package com.example.gettingstartedwithjetpackcompose.features.landingPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.repository.UserAuthRepository
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
class LandingPageViewModel @Inject constructor(private val userAuthRepository: UserAuthRepository)
    : ViewModel() {
    val landingPageState: StateFlow<LandingUiState> = userAuthRepository.userData.map{ sessionData ->
        LandingUiState(username = sessionData.username) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LandingUiState())

    private val _landingPageUiEvent = MutableSharedFlow<LandingEventUi>()
    val landingPageUiEvent: SharedFlow<LandingEventUi> = _landingPageUiEvent

    fun onEvent(event: LandingPageEvent) {
        viewModelScope.launch {
            when (event) {
                LandingPageEvent.NotesHomeClicked -> _landingPageUiEvent.emit(LandingEventUi.NavigateToNotesHome)
                LandingPageEvent.AccountsDashboardClicked -> _landingPageUiEvent.emit(LandingEventUi.NavigateToAccountsDashboard)
            }
        }
    }
}