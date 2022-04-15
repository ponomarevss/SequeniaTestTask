package ru.ponomarevss.sequeniatesttask.mvp.model.repo

import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film

interface IFilmsRepo {
    suspend fun getFilms(): List<Film>
}