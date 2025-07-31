package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AccountsRepository
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<AccountsDto>>(emptyList())
    val accounts: StateFlow<List<AccountsDto>> = _accounts.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init{
        loadAccounts()
    }

    fun loadAccounts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getAccounts()
                _accounts.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load accounts"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
