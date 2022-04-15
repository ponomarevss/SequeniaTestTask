package ru.ponomarevss.sequeniatesttask.mvp.presenter

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.runBlocking
import moxy.MvpPresenter
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.model.navigation.IScreens
import ru.ponomarevss.sequeniatesttask.mvp.model.repo.IFilmsRepo
import ru.ponomarevss.sequeniatesttask.mvp.presenter.list.IFilmsListPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmView
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmsView
import ru.ponomarevss.sequeniatesttask.mvp.view.list.FilmItemView
import javax.inject.Inject

class FilmPresenter(val film: Film) : MvpPresenter<FilmView>() {
    companion object {
        private const val YEAR = "Год:"
        private const val RATING = "Рейтинг:"
    }

    @Inject lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setTitle(film.localizedName)
        film.imageUrl?.let { viewState.loadImage(it) }
        viewState.setName(film.name)
        viewState.setYear("$YEAR ${film.year.toString()}")
        film.rating?.let { viewState.setRating("$RATING $it") }
        film.description?.let { viewState.setDescription(it) }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}