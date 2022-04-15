package ru.ponomarevss.sequeniatesttask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.chip.Chip
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.ponomarevss.sequeniatesttask.R
import ru.ponomarevss.sequeniatesttask.databinding.FragmentFilmBinding
import ru.ponomarevss.sequeniatesttask.databinding.FragmentFilmsBinding
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.model.image.IImageLoader
import ru.ponomarevss.sequeniatesttask.mvp.presenter.FilmPresenter
import ru.ponomarevss.sequeniatesttask.mvp.presenter.FilmsPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmView
import ru.ponomarevss.sequeniatesttask.ui.App
import ru.ponomarevss.sequeniatesttask.ui.BackButtonListener
import ru.ponomarevss.sequeniatesttask.ui.adapter.FilmsRVAdapter
import ru.ponomarevss.sequeniatesttask.ui.image.GlideImageLoader
import javax.inject.Inject

class FilmFragment : MvpAppCompatFragment(), FilmView, BackButtonListener {
    companion object {
        private const val FILM_ARG = "film"

        fun newInstance(film: Film) = FilmFragment().apply {
            arguments = Bundle().apply {
                putParcelable(FILM_ARG, film)
            }
        }
    }

    val presenter: FilmPresenter by moxyPresenter {
        val film = arguments?.getParcelable<Film>(FILM_ARG) as Film
        FilmPresenter(film).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var vb: FragmentFilmBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFilmBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun setTitle(text: String) {
        activity?.title = text
    }

    override fun setName(text: String) = with(vb) {
        this?.tvName?.text = text
    }

    override fun setYear(text: String) = with(vb) {
        this?.tvYear?.text = text
    }

    override fun setRating(text: String) = with(vb) {
        this?.tvRating?.text = text
    }

    override fun setDescription(text: String) = with(vb) {
        this?.tvDescription?.text = text
    }

    override fun loadImage(url: String) {
        vb?.ivFilm?.let { GlideImageLoader().loadInto(url, it) }
    }

    override fun backPressed() = presenter.backPressed()
}