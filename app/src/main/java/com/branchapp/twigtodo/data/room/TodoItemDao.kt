package com.branchapp.twigtodo.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.branchapp.twigtodo.data.dto.TodoItemDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todoItems")
    suspend fun getAll(): List<TodoItemDTO>

    @Query("SELECT * FROM todoItems")
    fun getAllFlow(): Flow<List<TodoItemDTO>>

    @Insert
    suspend fun insertAll(vararg items: TodoItemDTO)

    @Delete
    suspend fun delete(item: TodoItemDTO)
}