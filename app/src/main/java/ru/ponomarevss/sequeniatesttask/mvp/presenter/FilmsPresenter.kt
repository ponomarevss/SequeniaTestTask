package ru.ponomarevss.sequeniatesttask.mvp.presenter

import android.util.Log
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.runBlocking
import moxy.MvpPresenter
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.model.navigation.IScreens
import ru.ponomarevss.sequeniatesttask.mvp.model.repo.IFilmsRepo
import ru.ponomarevss.sequeniatesttask.mvp.presenter.list.IFilmsListPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmsView
import ru.ponomarevss.sequeniatesttask.mvp.view.list.FilmItemView
import javax.inject.Inject

class FilmsPresenter : MvpPresenter<FilmsView>() {
    companion object {
        private const val TITLE = "Главная"
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var repo: IFilmsRepo

    inner class FilmsListPresenter : IFilmsListPresenter {
        val films = mutableListOf<Film>()
        override var itemClickListener: ((FilmItemView) -> Unit)? = null

        override fun bindView(view: FilmItemView) {
            val film = films[view.pos]
            with(view) {
                with(film) {
                    setName(localizedName)
                    loadImage(imageUrl)
                }
            }
        }

        override fun getCount(): Int = films.size
    }

    val filmsListPresenter = FilmsListPresenter()
    private val initialFilmsList = mutableListOf<Film>()
    private val genres = mutableSetOf<String>()
    private var currentGenre: String? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        setChips()
        viewState.init()
        viewState.setTitle(TITLE)

        filmsListPresenter.itemClickListener = {
            val film = filmsListPresenter.films[it.pos]
            router.navigateTo(screens.film(film))
        }
    }

    private fun loadData() {
        initialFilmsList.clear()
        runBlocking {
            initialFilmsList.addAll(repo.getFilms())
        }
        filmsListPresenter.films.clear()
        filmsListPresenter.films.addAll(initialFilmsList.sortedBy { it.localizedName })
        viewState.update()
    }

    private fun setChips() {
        getGenres()
        genres.sorted().map {
            viewState.addChip(it)
        }
    }

    private fun getGenres(): MutableSet<String> = genres.apply {
        genres.clear()
        initialFilmsList.map {
            genres.addAll(it.genres)
        }
    }

    fun chipCheckedChangeListener(newGenre: String, isChecked: Boolean) {
        when {
            isChecked -> currentGenre = newGenre
            !isChecked && currentGenre == newGenre -> currentGenre = null
        }
        filmsListPresenter.films.clear()
        filmsListPresenter.films.addAll(filterFilmsList(currentGenre).sortedBy { it.localizedName })
        viewState.update()
    }

    private fun filterFilmsList(filter: String?): List<Film> {
        val filteredFilmsList = mutableListOf<Film>()
        filter?.let {
            initialFilmsList.map {
                if (it.genres.contains(filter)) {
                    filteredFilmsList.add(it)
                }
            }
        } ?: filteredFilmsList.addAll(initialFilmsList)
        return filteredFilmsList
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}