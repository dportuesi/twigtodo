package com.branchapp.twigtodo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.branchapp.twigtodo.LocalScrollBehavior
import com.branchapp.twigtodo.ui.theme.TwigTodoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwigTodoTopAppBar(
    title: String,
    titleIcon: Painter?,
) {
    val scrollBehavior = LocalScrollBehavior.current

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                titleIcon?.let {
                    Icon(painter = titleIcon, contentDescription = null)
                }
                Text(text = title)
            }
        },
        colors = twigTodoDefaultAppbarColors(),
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun twigTodoDefaultAppbarColors() = TopAppBarDefaults.topAppBarColors(
    containerColor = MaterialTheme.colorScheme.onPrimary,
    scrolledContainerColor = MaterialTheme.colorScheme.onPrimary,
    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    actionIconContentColor = Color.Unspecified,
)

@Preview
@Composable
private fun Preview() {
    TwigTodoTheme {
        TwigTodoTopAppBar(
            title = "Test Title",
            titleIcon = rememberVectorPainter(Icons.Outlined.Build)
        )
    }
}