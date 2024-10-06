package com.branchapp.twigtodo.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.branchapp.twigtodo.FabState
import com.branchapp.twigtodo.GlobalStateManager
import com.branchapp.twigtodo.R
import com.branchapp.twigtodo.data.model.TodoItem
import com.branchapp.twigtodo.ui.components.AddTodoDialog
import com.branchapp.twigtodo.ui.components.TodoCard
import com.branchapp.twigtodo.ui.theme.TwigTodoTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val uiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    val globalStateManager: GlobalStateManager = koinInject()

    var openDialog by remember { mutableStateOf(false) }

    globalStateManager.setFabState(
        FabState(
            onClick = { openDialog = true }
        )
    )

    HomeScreenLayout(
        uiState = uiState,
        cardClick = { item: TodoItem -> homeViewModel.onDeleteClicked(item) }
    )

    if (openDialog) {
        AddTodoDialog(
            onDismissRequest = { openDialog = false },
            onConfirmation = { text ->
                openDialog = false
                homeViewModel.addTodoItem(text)
            }
        )
    }
}

// Layout to allow for compose preview
@Composable
fun HomeScreenLayout(
    uiState: HomeScreenState,
    cardClick: (TodoItem) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (uiState) {
            is HomeScreenState.Loading ->
                Text(text = stringResource(R.string.loading_text))

            is HomeScreenState.TodoListLoaded -> {
                if (uiState.todoItems.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.empty_text)
                    )
                } else {
                    uiState.todoItems.map { card ->
                        TodoCard(
                            title = card.title,
                            onClick = { cardClick(card) }
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TwigTodoTheme {
        HomeScreenLayout(
            uiState = HomeScreenState.TodoListLoaded(
                todoItems = listOf(
                    TodoItem(
                        id = 1,
                        title = "test"
                    )
                )
            ),
            cardClick = { }
        )
    }
}