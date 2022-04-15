package ru.ponomarevss.sequeniatesttask.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}