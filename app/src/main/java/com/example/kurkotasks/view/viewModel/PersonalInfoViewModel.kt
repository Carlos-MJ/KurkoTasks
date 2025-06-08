package com.example.kurkotasks.view.viewModel

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurkotasks.core.ResultWrapper
import com.example.kurkotasks.databinding.FragmentProfileBinding
import com.example.kurkotasks.model.User
import com.example.kurkotasks.network.UserRepository
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.viewModel.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel@Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean>
        get() = _operationSuccess

    fun createUserInfo(userId: String, name: String, email: String, password: String){
        val user = User(id = userId, name, email, password)
        _loaderState.value = false
        viewModelScope.launch {
            when (val result = repository.createUser(user)){
                is ResultWrapper.Success ->{
                    _loaderState.value = false
                    _operationSuccess.value = true
                }
                is ResultWrapper.Error ->{
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }
}