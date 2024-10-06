package com.branchapp.twigtodo

import android.app.Application
import com.branchapp.twigtodo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TwigTodoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TwigTodoApplication)
            modules(appModule)
        }
    }
}