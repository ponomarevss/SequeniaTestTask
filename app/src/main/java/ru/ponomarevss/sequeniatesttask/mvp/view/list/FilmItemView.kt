package ru.ponomarevss.sequeniatesttask.mvp.view.list

interface FilmItemView: IItemView {
    fun setName(text: String)
    fun loadImage(url: String?)
}