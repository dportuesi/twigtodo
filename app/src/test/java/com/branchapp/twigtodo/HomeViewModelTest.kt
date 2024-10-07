package com.branchapp.twigtodo

import androidx.lifecycle.viewModelScope
import com.branchapp.twigtodo.data.model.TodoItem
import com.branchapp.twigtodo.data.repository.TodoRepository
import com.branchapp.twigtodo.ui.screens.home.HomeScreenState
import com.branchapp.twigtodo.ui.screens.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var todoRepository: TodoRepository

    @Before
    fun initViewModel() {
        todoRepository = mockk<TodoRepository>()
        homeViewModel = HomeViewModel(
            todoItemRepository = todoRepository,
            dispatcher = mainDispatcherRule.dispatcher
        )
    }

    @Test
    fun `viewModel created loads correct todo items`() = runTest {
        // arrange
        coEvery { todoRepository.getTodoItemsFlow() } returns
            flow {
                emit(listOf(
                    TodoItem(
                        id = 1,
                        title = "test"
                    ),
                    TodoItem(
                        id = 2,
                        title = "second"
                    )
                ))
            }

        advanceUntilIdle()

        // act
        initViewModel()

        // assert
        val homeUiStateWatcher = mutableListOf<HomeScreenState>()
        homeViewModel.viewModelScope.launch {
            homeViewModel.homeUiState.collect { state ->
                homeUiStateWatcher.add(state)
            }
        }

        advanceUntilIdle()

        val expected = HomeScreenState.TodoListLoaded(
            todoItems = listOf(
                TodoItem(
                    id = 1,
                    title = "test"
                ),
                TodoItem(
                    id = 2,
                    title = "second"
                )
            )
        )

        verify { todoRepository.getTodoItemsFlow() }
        assertEquals(HomeScreenState.Loading, homeUiStateWatcher.first())
        assertEquals(expected, homeUiStateWatcher.last())
    }
}