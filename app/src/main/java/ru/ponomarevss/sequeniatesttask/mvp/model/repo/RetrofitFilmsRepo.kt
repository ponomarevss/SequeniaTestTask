package ru.ponomarevss.sequeniatesttask.mvp.model.repo

import kotlinx.coroutines.withTimeout
import ru.ponomarevss.sequeniatesttask.mvp.model.api.IDataSource
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import java.io.IOException

class RetrofitFilmsRepo(private val api: IDataSource) : IFilmsRepo {

    override suspend fun getFilms(): List<Film> {
        try {
            val result = withTimeout(5_000) {
                api.getFilms()
            }
            return result.films
        } catch (e: Throwable) {
            throw IOException("Unable to fetch films", e)
        }
    }
}