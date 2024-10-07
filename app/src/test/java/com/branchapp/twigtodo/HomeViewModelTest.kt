package com.branchapp.twigtodo

import androidx.lifecycle.viewModelScope
import com.branchapp.twigtodo.data.model.TodoItem
import com.branchapp.twigtodo.data.repository.TodoRepository
import com.branchapp.twigtodo.ui.screens.home.HomeScreenState
import com.branchapp.twigtodo.ui.screens.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifySequence
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel
    private var todoRepository: TodoRepository = mockk<TodoRepository>(relaxed = true)

    @Before
    fun initViewModel() {
        homeViewModel = HomeViewModel(
            todoItemRepository = todoRepository,
            dispatcher = mainDispatcherRule.dispatcher
        )
    }

    @Test
    fun `viewModel created loads correct todo items`() = runTest {
        // arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val homeUiStateWatcher = mutableListOf<HomeScreenState>()
        val uiWatcherJob  = homeViewModel.viewModelScope.launch {
            homeViewModel.homeUiState.collect { state ->
                homeUiStateWatcher.add(state)
            }
        }

        coEvery { todoRepository.getTodoItemsFlow() }.coAnswers {
            flow {
                emit(
                    listOf(
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
            }
        }

        advanceUntilIdle()

        // act
        initViewModel()

        advanceUntilIdle()

        // assert
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
        advanceUntilIdle()
        assertEquals(HomeScreenState.Loading, homeUiStateWatcher.first())
        assertEquals(expected, homeUiStateWatcher.last())

        uiWatcherJob.cancel()
    }

    @Test
    fun `every onDeleteClicked calls delete on repository`() = runTest {
        // arrange
        coEvery { todoRepository.deleteTodoItem(any()) } just runs
        every { todoRepository.getTodoItemsFlow() } returns
                flowOf(
                    listOf(
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

        initViewModel()
        val testCard = TodoItem(
            id = 1,
            title = "test card"
        )

        // act
        homeViewModel.onDeleteClicked(testCard)
        advanceUntilIdle()

        // assert
        coVerify(exactly = 1) { todoRepository.deleteTodoItem(testCard) }
    }
}