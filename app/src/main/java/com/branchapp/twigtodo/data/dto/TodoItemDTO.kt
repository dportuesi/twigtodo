package com.branchapp.twigtodo.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.branchapp.twigtodo.data.model.TodoItem

@Entity(tableName = "todoItems")
data class TodoItemDTO(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
) {
    /**
     * Converts db object (DTO) to Domain Object
     */
    fun toTodoItem(): TodoItem = TodoItem(
        id = this.uid,
        title = this.title
    )
}