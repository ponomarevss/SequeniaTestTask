package ru.ponomarevss.sequeniatesttask.di

import dagger.Component
import ru.ponomarevss.sequeniatesttask.di.module.*
import ru.ponomarevss.sequeniatesttask.mvp.presenter.FilmPresenter
import ru.ponomarevss.sequeniatesttask.mvp.presenter.FilmsPresenter
import ru.ponomarevss.sequeniatesttask.mvp.presenter.MainPresenter
import ru.ponomarevss.sequeniatesttask.ui.activity.MainActivity
import ru.ponomarevss.sequeniatesttask.ui.fragment.FilmFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        CiceroneModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(filmFragment: FilmFragment)
    fun inject(mainPresenter: MainPresenter)
    fun inject(filmsPresenter: FilmsPresenter)
    fun inject(filmPresenter: FilmPresenter)
}