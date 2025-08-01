package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    init {
        loadAccounts()
    }


    fun loadAccounts(query: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getAccounts(query)
                _accounts.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load accounts"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
