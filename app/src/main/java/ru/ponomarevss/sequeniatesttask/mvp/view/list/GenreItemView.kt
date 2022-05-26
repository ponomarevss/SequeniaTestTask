package ru.ponomarevss.sequeniatesttask.mvp.view.list

interface GenreItemView: IItemView {
    fun setName(text: String)
    fun setState(isChecked: Boolean)
    fun setListener()
}