package ru.ponomarevss.sequeniatesttask.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface FilmView: MvpView {
    fun setTitle(text: String)
    fun setName(text: String)
    fun setYear(text: String)
    fun setRating(text: String)
    fun setDescription(text: String)
    fun loadImage(url: String)
    fun setHomeButton()
}