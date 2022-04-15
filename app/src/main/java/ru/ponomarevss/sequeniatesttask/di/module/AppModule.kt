package ru.ponomarevss.sequeniatesttask.di.module

import dagger.Module
import dagger.Provides
import ru.ponomarevss.sequeniatesttask.ui.App

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app
}