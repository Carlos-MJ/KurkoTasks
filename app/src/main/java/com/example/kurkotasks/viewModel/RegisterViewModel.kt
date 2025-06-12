package com.example.kurkotasks.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurkotasks.core.ResultWrapper
import com.example.kurkotasks.network.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel(){
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val _isUserCreted = MutableLiveData<String>()
    val isUserCreted: LiveData<String>
        get() = _isUserCreted

    fun requestSignUp(email: String, password: String) {
        _loaderState.value = false
        viewModelScope.launch {
            when( val result = repository.requestSignUp(email, password)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _isUserCreted.value = result.data.uid
                }

                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    Log.e("Firebase", "Se ha generado un error")
                }
            }
        }
    }
}