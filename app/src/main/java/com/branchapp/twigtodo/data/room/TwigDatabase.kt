package com.branchapp.twigtodo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.branchapp.twigtodo.data.dto.TodoItemDTO

@Database(entities = [TodoItemDTO::class], version = 1)
abstract class TwigDatabase : RoomDatabase() {
    abstract fun getTodoItemDao(): TodoItemDao
}