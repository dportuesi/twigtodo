package com.branchapp.twigtodo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwigToDoScreen(
    snackbarHostState: SnackbarHostState,
    topBar: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                CompositionLocalProvider(
                    LocalScrollBehavior provides scrollBehavior
                ) {
                    topBar()
                }
            },
            floatingActionButton = fab
        ) {
            Box(
                Modifier
                    .padding(it)
            ) {
                CompositionLocalProvider(
                    LocalScrollState provides scrollState
                ) {
                    content()
                }
            }
        }
    }
}