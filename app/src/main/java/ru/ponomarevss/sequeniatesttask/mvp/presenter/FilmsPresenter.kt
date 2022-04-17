package ru.ponomarevss.sequeniatesttask.mvp.presenter

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.runBlocking
import moxy.MvpPresenter
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Genre
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

    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens
    @Inject lateinit var repo: IFilmsRepo

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
    

    val genresList = mutableListOf<Genre>()
    
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        setGenresList()
        setFilmsListPresenter()
        setChips()
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
        runBlocking {
            initialFilmsList.addAll(repo.getFilms())
        }
    }
    
    private fun setFilmsListPresenter() {
        filmsListPresenter.films.clear()
        filmsListPresenter.films.addAll(initialFilmsList.sortedBy { it.localizedName })
        viewState.update()
    }

    private fun setGenresList() {
        val genres = mutableSetOf<String>()
        initialFilmsList.map { genres.addAll(it.genres) }
        genres.sorted().map { genresList.add(Genre(it)) }
    }

    private fun setChips() {
        viewState.addChips()
    }

    fun chipCheckedChangeListener(newGenre: String, isChecked: Boolean) {
        when {
            isChecked -> {
                genresList.map {
                    it.isSelected = it.name == newGenre
                }
            }
            !isChecked -> {
                genresList.map {
                    if (it.name == newGenre && it.isSelected) {
                        it.isSelected = false
                    }
                }
            }
        }

        filterFilmsList()
    }

    private fun filterFilmsList() {
        var currentGenre: String? = null
        val filteredFilmsList = mutableListOf<Film>()

        genresList.map {
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
        viewState.update()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}