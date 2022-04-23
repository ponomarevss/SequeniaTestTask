package ru.ponomarevss.sequeniatesttask.mvp.view

import android.widget.CompoundButton
import com.google.android.material.chip.Chip
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEnd
interface FilmsView: MvpView {
    fun init()
    fun update()
    fun addChips()
    fun setTitle(text: String)
    fun setHomeButton()
    fun setAlert(text: String)
}