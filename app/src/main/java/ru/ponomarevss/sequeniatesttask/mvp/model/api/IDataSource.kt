package ru.ponomarevss.sequeniatesttask.mvp.model.api

import retrofit2.http.GET
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Respond

interface IDataSource {

    @GET("sequeniatesttask/films.json")
    suspend fun getFilms(): Respond
}