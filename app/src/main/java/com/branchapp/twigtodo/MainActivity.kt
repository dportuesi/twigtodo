package com.branchapp.twigtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.branchapp.twigtodo.ui.components.TwigFabButton
import com.branchapp.twigtodo.ui.components.TwigTodoTopAppBar
import com.branchapp.twigtodo.ui.screens.home.HomeScreen
import com.branchapp.twigtodo.ui.theme.TwigTodoTheme
import org.koin.compose.koinInject
import timber.log.Timber
import timber.log.Timber.Forest.plant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        setContent {
            val globalState: GlobalStateManager = koinInject()
            val fabState by globalState.fabState.collectAsStateWithLifecycle()

            val snackbarHostState = remember { SnackbarHostState() }

            TwigTodoTheme {
                CompositionLocalProvider(
                    LocalSnackbarHostState provides snackbarHostState,
                ) {
                    Box {
                        TwigToDoScreen(
                            snackbarHostState = snackbarHostState,
                            topBar = {
                                TwigTodoTopAppBar(
                                    title = stringResource(R.string.titlebar_text),
                                    titleIcon = rememberVectorPainter(Icons.Default.Home)
                                )
                            },
                            fab = {
                                TwigFabButton(
                                    twigFabState = fabState
                                )
                            }
                        ) {
                            // normally a navgraph goes here
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}