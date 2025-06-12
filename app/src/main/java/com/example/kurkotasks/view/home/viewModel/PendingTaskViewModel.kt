package com.example.kurkotasks.view.home.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurkotasks.network.TaskRepository
import kotlinx.coroutines.launch
import com.example.kurkotasks.model.Task
import com.example.kurkotasks.core.ResultWrapper

@HiltViewModel
class PendingTaskViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>>
        get() = _taskList

    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _taskInfo = MutableLiveData<List<Task>>()
    val taskInfo: LiveData<List<Task>>
        get() = _taskInfo

    fun loadTasks() {
        viewModelScope.launch {
            _loaderState.postValue(true)

            when (val result = repository.getTaskList()) {
                is ResultWrapper.Success -> _taskInfo.postValue(result.data)
                is ResultWrapper.Error -> _taskInfo.postValue(emptyList())
            }

            _loaderState.postValue(false)
        }
    }
}