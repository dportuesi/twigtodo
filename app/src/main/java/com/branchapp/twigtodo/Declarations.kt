package com.branchapp.twigtodo

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.compositionLocalOf

// here is where i normally declare "app wide shared" things,
// such snackbar state

@OptIn(ExperimentalMaterial3Api::class)
val LocalScrollBehavior =
    compositionLocalOf<TopAppBarScrollBehavior?> { null }

val LocalScrollState =
    compositionLocalOf<ScrollState> { error("No ScrollState") }

val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState") }