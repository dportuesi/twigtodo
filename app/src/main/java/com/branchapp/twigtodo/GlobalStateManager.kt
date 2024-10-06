package com.branchapp.twigtodo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GlobalStateManager {
    private val _fabState = MutableStateFlow(FabState())
    val fabState = _fabState.asStateFlow()

    fun setFabState(state: FabState) {
        _fabState.value = state
    }
}

data class FabState(
    val onClick: () -> Unit = {},
    val showFab: Boolean = false
)