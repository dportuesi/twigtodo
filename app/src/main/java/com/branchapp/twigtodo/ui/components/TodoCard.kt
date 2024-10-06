package com.branchapp.twigtodo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.branchapp.twigtodo.R
import com.branchapp.twigtodo.ui.theme.TwigTodoTheme

@Composable
fun TodoCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = title
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        onClick()
                    },
                painter = rememberVectorPainter(Icons.Filled.Delete),
                contentDescription = stringResource(R.string.cd_delete_todo)
            )
        }
    }
}

@Preview
@Composable
fun TodoCardPreview() {
    TwigTodoTheme {
        TodoCard(
            title = "preview",
            onClick = { }
        )
    }
}