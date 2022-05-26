package ru.ponomarevss.sequeniatesttask.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

@AddToEnd
interface FilmsView: MvpView {
    fun init()
    fun updateFilms()
    fun updateGenres()
    fun setTitle(text: String)
    fun setHomeButton()
    fun setAlert(text: String)
}