package com.example.kurkotasks.network

import android.util.Log
import com.example.kurkotasks.core.ResultWrapper
import com.example.kurkotasks.core.safeCall
import com.example.kurkotasks.model.Task
import com.example.kurkotasks.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.util.Date
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val taskCollection = firestore.collection("Tasks")

    suspend fun createTask(task: Task): ResultWrapper<Void> = safeCall {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun getTask(taskId: String): ResultWrapper<Task> = safeCall {
        val snapshot = taskCollection.document(taskId).get().await()
        snapshot.toObject(Task::class.java) ?: throw Exception("Tarea no encontrada")
    }

    suspend fun getTaskList(): ResultWrapper<List<Task>> = safeCall {
        val snapshot = firestore.collection("Tasks").get().await()
        snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
    }

    suspend fun updateTask(task: Task): ResultWrapper<Void> = safeCall {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(taskid: String): ResultWrapper<Void> = safeCall {
        taskCollection.document(taskid).delete().await()
    }
}