package ru.ponomarevss.sequeniatesttask.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmView
import javax.inject.Inject

class FilmPresenter(val film: Film) : MvpPresenter<FilmView>() {

    @Inject lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setTitle(film.localizedName)
        film.imageUrl?.let { viewState.loadImage(it) }
        viewState.setName(film.name)
        viewState.setYear("${film.year}")
        film.rating?.let { viewState.setRating("$it") }
        film.description?.let { viewState.setDescription(it) }
        viewState.setHomeButton()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}