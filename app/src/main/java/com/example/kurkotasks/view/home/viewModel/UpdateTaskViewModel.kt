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
import java.util.Date
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

    fun updateTaskInfo(taskId: String, name: String, description: String, bornDate: Date) {
        val task = Task(id = taskId, name = name, description = description, bornDate = bornDate)
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.updateTask(task)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _operationSuccess.value = true
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message ?: "Error desconocido al actualizar tarea"
                    Log.e("TaskViewModel", errorMessage)
                }
            }
        }
    }
}