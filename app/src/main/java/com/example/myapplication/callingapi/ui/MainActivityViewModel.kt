package com.example.myapplication.callingapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.callingapi.data.TestResponse
import com.example.myapplication.callingapi.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: TestRepository) :
    ViewModel() {

    private val _permissionResponse = MutableLiveData<TestResponse?>()
    val permissionResponse: LiveData<TestResponse?> = _permissionResponse

    private val _responseCode = MutableLiveData<Int?>()
    val responseCode: LiveData<Int?> = _responseCode

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchPermissionResponse() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getTest() // Using Result<TestResponse>
                if (result.isSuccess) {
                    val response = result.getOrNull()
                    _permissionResponse.value = response
                    _responseCode.value = 200 // Assuming HTTP status code 200 on success
                } else {
                    val exception = result.exceptionOrNull()
                    _error.value = exception?.message ?: "Unknown error occurred"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearPermissionResponse() {
        _permissionResponse.value = null
    }
}

