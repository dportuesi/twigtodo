package com.branchapp.twigtodo.di

import androidx.room.Room
import com.branchapp.twigtodo.GlobalStateManager
import com.branchapp.twigtodo.data.repository.TodoRepository
import com.branchapp.twigtodo.data.repository.TodoRepositoryImpl
import com.branchapp.twigtodo.data.room.TodoItemDao
import com.branchapp.twigtodo.data.room.TwigDatabase
import com.branchapp.twigtodo.ui.screens.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    singleOf(::GlobalStateManager)

    single {
        Room.databaseBuilder(
            androidContext(),
            TwigDatabase::class.java,
            "twig-todo-database"
        ).build()
    }

    single<TodoItemDao> {
        val database = get<TwigDatabase>()
        database.getTodoItemDao()
    }

    single<TodoRepository> {
        TodoRepositoryImpl(
            todoItemDao = get()
        )
    }

    viewModel {
        HomeViewModel(
            get()
        )
    }
}