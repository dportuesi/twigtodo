package com.branchapp.twigtodo.data.repository

import com.branchapp.twigtodo.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodoItemsFlow(): Flow<List<TodoItem>>
    suspend fun deleteTodoItem(todoItem: TodoItem)
    suspend fun insertTodoItem(item: TodoItem)
}