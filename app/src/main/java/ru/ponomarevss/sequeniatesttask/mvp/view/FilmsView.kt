package ru.ponomarevss.sequeniatesttask.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEnd
interface FilmsView: MvpView {
    fun init()
    fun update()
    fun addChip(text: String)
    fun setTitle(text: String)
}