package com.branchapp.twigtodo.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branchapp.twigtodo.data.model.TodoItem
import com.branchapp.twigtodo.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    val todoItemRepository: TodoRepository
): ViewModel() {
    // start our screen off loading
    private val _homeUiState: MutableStateFlow<HomeScreenState> = MutableStateFlow(
        HomeScreenState.Loading
    )
    val homeUiState = _homeUiState.asStateFlow()

    init {
        // Listen for DB changes. This means our DB is the source
        // of truth, and drives our UI state
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // downside is this is a heavy IO thread operation. Could swap to manual
                // CRUD calls to avoid constant listening
                todoItemRepository.getTodoItemsFlow().collect { items ->
                    updateTodoItemsInUI(items)
                }
            }
        }

    }

    fun onDeleteClicked(card: TodoItem) {
        viewModelScope.launch {
            todoItemRepository.deleteTodoItem(card)
        }
    }

    fun addTodoItem(title: String) {
        viewModelScope.launch {
            todoItemRepository.insertTodoItem(
                TodoItem(
                    title = title
                )
            )
        }
    }

    private fun updateTodoItemsInUI(todoItems: List<TodoItem>) {
        _homeUiState.value = HomeScreenState.TodoListLoaded(
            todoItems = todoItems
        )
    }
}

sealed class HomeScreenState() {
    object Loading: HomeScreenState()
    data class TodoListLoaded(
        val todoItems: List<TodoItem>
    ): HomeScreenState()

    // Could add error state here as well!
}