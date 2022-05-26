package ru.ponomarevss.sequeniatesttask.mvp.presenter

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.runBlocking
import moxy.MvpPresenter
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Genre
import ru.ponomarevss.sequeniatesttask.mvp.model.navigation.IScreens
import ru.ponomarevss.sequeniatesttask.mvp.model.repo.IFilmsRepo
import ru.ponomarevss.sequeniatesttask.mvp.presenter.list.IFilmsListPresenter
import ru.ponomarevss.sequeniatesttask.mvp.presenter.list.IGenresListPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmsView
import ru.ponomarevss.sequeniatesttask.mvp.view.list.FilmItemView
import ru.ponomarevss.sequeniatesttask.mvp.view.list.GenreItemView
import javax.inject.Inject

class FilmsPresenter : MvpPresenter<FilmsView>() {
    companion object {
        private const val TITLE = "Главная"
    }

    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens
    @Inject lateinit var repo: IFilmsRepo

    inner class FilmsListPresenter : IFilmsListPresenter {
        val films = mutableListOf<Film>()
        override var itemClickListener: ((FilmItemView) -> Unit)? = null

        override fun bindView(view: FilmItemView) {
            val film = films[view.pos]
            view.setName(film.localizedName)
            view.loadImage(film.imageUrl)
        }

        override fun getCount(): Int = films.size
    }

    inner class GenresListPresenter : IGenresListPresenter {
        val genres = mutableListOf<Genre>()
        override var itemClickListener: ((GenreItemView) -> Unit)? = null

        override fun bindView(view: GenreItemView) {
            val genre = genres[view.pos]
            view.setName(genre.name)
            view.setState(genre.isSelected)
            view.setListener()
        }

        override fun buttonClicked(pos: Int) {
            for (i in genres.indices) {
                if (i == pos) {
                    genres[i].isSelected = !genres[i].isSelected
                } else
                    genres[i].isSelected = false
            }
            viewState.updateGenres()
            filterFilmsList()
        }

        override fun getCount(): Int = genres.size
    }

    private val initialFilmsList = mutableListOf<Film>()
    val filmsListPresenter = FilmsListPresenter()
    val genresListPresenter = GenresListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        setGenresListPresenter()
        setFilmsListPresenter()
        viewState.init()
        viewState.setTitle(TITLE)
        viewState.setHomeButton()

        filmsListPresenter.itemClickListener = {
            val film = filmsListPresenter.films[it.pos]
            router.navigateTo(screens.film(film))
        }
    }

    private fun loadData() {
        initialFilmsList.clear()
        try {
            runBlocking {
                initialFilmsList.addAll(repo.getFilms())
            }
        } catch (e: Throwable) {
            viewState.setAlert(e.message.toString())
        }
    }
    
    private fun setFilmsListPresenter() {
        filmsListPresenter.films.clear()
        filmsListPresenter.films.addAll(initialFilmsList.sortedBy { it.localizedName })
        viewState.updateFilms()
    }

    private fun setGenresListPresenter() {
        val names = mutableSetOf<String>()
        initialFilmsList.map { names.addAll(it.genres) }
        genresListPresenter.genres.clear()
        names.sorted().map { genresListPresenter.genres.add(Genre(it)) }
        viewState.updateGenres()
    }

    private fun filterFilmsList() {
        var currentGenre: String? = null
        val filteredFilmsList = mutableListOf<Film>()

        genresListPresenter.genres.map {
            if (it.isSelected) {
                currentGenre = it.name
            }
        }

        currentGenre?.let {
            initialFilmsList.map {
                if (it.genres.contains(currentGenre)) {
                    filteredFilmsList.add(it)
                }
            }
        } ?: filteredFilmsList.addAll(initialFilmsList)

        filmsListPresenter.films.clear()
        filmsListPresenter.films.addAll(filteredFilmsList.sortedBy { it.localizedName })
        viewState.updateFilms()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}