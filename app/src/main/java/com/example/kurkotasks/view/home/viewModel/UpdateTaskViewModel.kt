package com.example.kurkotasks.view.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurkotasks.model.Task
import com.example.kurkotasks.network.TaskRepository
import com.example.kurkotasks.core.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean>
        get() = _operationSuccess

    fun updateTaskInfo(updatedTask: Task) {
        viewModelScope.launch {
            when (val result = repository.updateTask(updatedTask)) {
                is ResultWrapper.Success -> {
                    _operationSuccess.postValue(true)
                }
                is ResultWrapper.Error -> {
                    Log.e("TaskViewModel", "Error al actualizar tarea", result.exception)
                }
            }
        }
    }
}