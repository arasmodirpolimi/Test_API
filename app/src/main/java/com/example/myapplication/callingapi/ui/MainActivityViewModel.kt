package com.example.myapplication.callingapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.callingapi.data.TestResponse
import com.example.myapplication.callingapi.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: TestRepository) :
    ViewModel() {

    private val disposables = CompositeDisposable()

    private val permissionResponseSubject = BehaviorSubject.create<TestResponse>()
    private val loadingSubject = BehaviorSubject.create<Boolean>()
    private val errorSubject = PublishSubject.create<String>()

    // Expose the reactive streams to the activity
    val permissionResponseObservable = permissionResponseSubject.hide()!!
    val loadingObservable = loadingSubject.hide()!!
    val errorObservable = errorSubject.hide()!!

    /**
     * Fetches permission response by subscribing to the repository's reactive stream.
     */
    fun fetchPermissionResponse() {
        loadingSubject.onNext(true)
        val disposable = repository.getTest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    // Emit the response to the observers
                    loadingSubject.onNext(false)
                    permissionResponseSubject.onNext(response)
                },
                { throwable ->
                    // Emit the error to the observers
                    loadingSubject.onNext(false)
                    errorSubject.onNext(throwable.message ?: "Unknown Error")
                }
            )

        disposables.add(disposable) // Add the subscription to the CompositeDisposable
    }

    /**
     * Clear all subscriptions when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


//    private val _permissionResponse = MutableLiveData<TestResponse?>()
//    val permissionResponse: LiveData<TestResponse?> = _permissionResponse
//
//    private val _responseCode = MutableLiveData<Int?>()
//    val responseCode: LiveData<Int?> = _responseCode
//
//    private val _error = MutableLiveData<String?>()
//    val error: LiveData<String?> = _error
//
//    private val _loading = MutableLiveData<Boolean>()
//    val loading: LiveData<Boolean> = _loading
//
//    fun fetchPermissionResponse() {
//        viewModelScope.launch {
//            _loading.value = true
//            try {
//                val result = repository.getTest() // Using Result<TestResponse>
//                if (result.isSuccess) {
//                    val response = result.getOrNull()
//                    _permissionResponse.value = response
//                    _responseCode.value = 200 // Assuming HTTP status code 200 on success
//                } else {
//                    val exception = result.exceptionOrNull()
//                    _error.value = exception?.message ?: "Unknown error occurred"
//                }
//            } catch (e: Exception) {
//                _error.value = e.message ?: "Unknown error occurred"
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//
//    fun clearError() {
//        _error.value = null
//    }
//
//    fun clearPermissionResponse() {
//        _permissionResponse.value = null
//    }
}

