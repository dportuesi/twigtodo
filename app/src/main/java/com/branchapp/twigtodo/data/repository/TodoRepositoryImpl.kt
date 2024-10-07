package com.branchapp.twigtodo.data.repository

import com.branchapp.twigtodo.data.model.TodoItem
import com.branchapp.twigtodo.data.room.TodoItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(
    val todoItemDao: TodoItemDao
): TodoRepository {
    override fun getTodoItemsFlow(): Flow<List<TodoItem>> {
        // Map our DTO list to Domain model list
        return todoItemDao.getAllFlow().map { list ->
            list.map { it.toTodoItem() }
        }
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        todoItemDao.delete(todoItem.toDTO())
    }

    override suspend fun insertTodoItem(item: TodoItem) = withContext(Dispatchers.IO) {
        todoItemDao.insertAll(item.toDTO())
    }
}