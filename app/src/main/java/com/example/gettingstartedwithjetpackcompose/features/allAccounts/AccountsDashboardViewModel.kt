package com.example.gettingstartedwithjetpackcompose.features.allAccounts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gettingstartedwithjetpackcompose.data.repository.AccountsRepository
import com.example.gettingstartedwithjetpackcompose.data.network.accountsNetwork.AccountsDto
import com.example.gettingstartedwithjetpackcompose.data.network.accountsNetwork.FullAccountDetailsDto
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
                Log.d("ERROR", e.message.toString())
                _error.value = "Failed to load accounts"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    //for the account details
    private val _accountDetails = MutableStateFlow<FullAccountDetailsDto?>(null)
    val accountDetails: StateFlow<FullAccountDetailsDto?> = _accountDetails.asStateFlow()

    private val _detailsLoading = MutableStateFlow(false)
    val detailsLoading: StateFlow<Boolean> = _detailsLoading.asStateFlow()

    private val _detailsError = MutableStateFlow<String?>(null)
    val detailsError: StateFlow<String?> = _detailsError.asStateFlow()

    fun loadAccountDetails(uuid: String) {
        viewModelScope.launch {
            _detailsLoading.value = true
            try {
                val details = repository.getFullAccountDetails(uuid = uuid, extraInfo = true)
                Log.d("DETAILS_API", "Loaded account details: ${Gson().toJson(details)}")
                _accountDetails.value = details
                _detailsError.value = null
            } catch (e: Exception) {
                _detailsError.value = e.message ?: "Unable to load account details"
            } finally {
                _detailsLoading.value = false
            }
        }
    }
}
