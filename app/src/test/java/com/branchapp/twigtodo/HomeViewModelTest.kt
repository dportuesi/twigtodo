package com.branchapp.twigtodo

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
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
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

        // act
        initViewModel()

        // assert
        assertEquals(HomeScreenState.Loading, homeViewModel.homeUiState.value)

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
        assertEquals(expected, homeViewModel.homeUiState.value)
        verify { todoRepository.getTodoItemsFlow() }
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

    @Test
    fun `every addTodoItem calls insert on repository`() = runTest {
        // arrange
        coEvery { todoRepository.insertTodoItem(any()) } just runs
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


        // act
        homeViewModel.addTodoItem("test card")
        advanceUntilIdle()

        // assert
        val expected = TodoItem(
            id = null,
            title = "test card"
        )
        coVerify(exactly = 1) { todoRepository.insertTodoItem(expected) }
    }

    @Test
    fun `every addTodoItem calls shows error`() = runTest {
        // arrange
        coEvery { todoRepository.insertTodoItem(any()) } throws Exception("test exception")
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


        // act
        advanceUntilIdle()
        homeViewModel.addTodoItem("test card")
        advanceUntilIdle()

        // assert
        val expected = TodoItem(
            id = null,
            title = "test card"
        )
        coVerify(exactly = 1) { todoRepository.insertTodoItem(expected) }
        assertEquals(HomeScreenState.Error("Error creating item."), homeViewModel.homeUiState.value)
    }

    @Test
    fun `every onDeleteClicked calls shows error`() = runTest {
        // arrange
        coEvery { todoRepository.deleteTodoItem(any()) } throws Exception("test exception")
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
        advanceUntilIdle()
        homeViewModel.onDeleteClicked(testCard)
        advanceUntilIdle()

        // assert
        coVerify(exactly = 1) { todoRepository.deleteTodoItem(testCard) }
        assertEquals(HomeScreenState.Error("Error deleting item."), homeViewModel.homeUiState.value)
    }
}