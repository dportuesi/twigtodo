package com.branchapp.twigtodo.data.model

import com.branchapp.twigtodo.data.dto.TodoItemDTO

data class TodoItem(
    val id: Int? = null,
    val title: String
) {
    /**
     * Converts domain model to DTO model
     */
    fun toDTO(): TodoItemDTO {
        // zero will auto generate uid
        return TodoItemDTO(
            uid = id ?: 0,
            title = title
        )
    }
}