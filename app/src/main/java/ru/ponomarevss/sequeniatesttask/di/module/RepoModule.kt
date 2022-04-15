package ru.ponomarevss.sequeniatesttask.di.module

import dagger.Module
import dagger.Provides
import ru.ponomarevss.sequeniatesttask.mvp.model.api.IDataSource
import ru.ponomarevss.sequeniatesttask.mvp.model.repo.IFilmsRepo
import ru.ponomarevss.sequeniatesttask.mvp.model.repo.RetrofitFilmsRepo
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun filmsRepo(
        api: IDataSource,
    ): IFilmsRepo = RetrofitFilmsRepo(api)

}