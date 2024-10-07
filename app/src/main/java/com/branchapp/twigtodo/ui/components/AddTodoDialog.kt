package com.branchapp.twigtodo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.branchapp.twigtodo.R
import com.branchapp.twigtodo.ui.theme.TwigTodoTheme

@Composable
fun AddTodoDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        icon = {
            Icon(Icons.Outlined.AddCircle, stringResource(R.string.cd_add_todo))
        },
        title = {
            Text(text = stringResource(R.string.dialog_title_add))
        },
        text = {
            Column {
                Text(text = stringResource(R.string.dialog_desc_add))
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(stringResource(R.string.dialog_input_placeholder)) }
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                enabled = text.length > 1,
                onClick = {
                    onConfirmation(text)
                }
            ) {
                Text(stringResource(R.string.dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.dialog_cancel))
            }
        }
    )
}

@Preview
@Composable
fun AddTodoDialogPreview() {
    TwigTodoTheme {
        AddTodoDialog(
            onDismissRequest = { },
            onConfirmation = { }
        )
    }
}