package ru.ponomarevss.sequeniatesttask.ui

import android.app.Application
import ru.ponomarevss.sequeniatesttask.di.AppComponent
import ru.ponomarevss.sequeniatesttask.di.DaggerAppComponent

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
//            .appModule(AppModule(this))
            .build()
    }
}