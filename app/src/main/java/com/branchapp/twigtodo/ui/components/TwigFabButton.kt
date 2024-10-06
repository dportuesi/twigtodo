package com.branchapp.twigtodo.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.branchapp.twigtodo.FabState
import com.branchapp.twigtodo.R
import com.branchapp.twigtodo.ui.theme.TwigTodoTheme

@Composable
fun TwigFabButton(
    twigFabState: FabState
) {
    SmallFloatingActionButton(
        onClick = { twigFabState.onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, stringResource(R.string.cd_add_todo))
    }
}

@Composable
@Preview
fun PreviewTwigAddButton() {
    TwigTodoTheme {
        TwigFabButton(
            FabState(onClick = {})
        )
    }
}