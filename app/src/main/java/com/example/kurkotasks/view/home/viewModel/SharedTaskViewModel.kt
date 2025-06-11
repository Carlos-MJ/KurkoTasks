package com.example.kurkotasks.view.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.kurkotasks.network.TaskRepository
import com.example.kurkotasks.model.Task

@HiltViewModel
class SharedTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _selectedTask = MutableLiveData<Task?>()
    val selectedTask: LiveData<Task?> get() = _selectedTask

    fun setSelectedTask(task: Task) {
        _selectedTask.postValue(task)
    }
}

