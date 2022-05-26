package ru.ponomarevss.sequeniatesttask.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.ponomarevss.sequeniatesttask.databinding.FragmentFilmsBinding
import ru.ponomarevss.sequeniatesttask.mvp.presenter.FilmsPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmsView
import ru.ponomarevss.sequeniatesttask.ui.App
import ru.ponomarevss.sequeniatesttask.ui.BackButtonListener
import ru.ponomarevss.sequeniatesttask.ui.adapter.FilmsRVAdapter
import ru.ponomarevss.sequeniatesttask.ui.adapter.GenresRVAdapter
import ru.ponomarevss.sequeniatesttask.ui.image.GlideImageLoader

class FilmsFragment: MvpAppCompatFragment(), FilmsView, BackButtonListener {
    companion object {
        private const val SPAN_COUNT = 2
        fun newInstance() = FilmsFragment()
    }

    val presenter: FilmsPresenter by moxyPresenter {
        FilmsPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var filmsAdapter: FilmsRVAdapter? = null
    private var genresAdapter: GenresRVAdapter? = null
    private var vb: FragmentFilmsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFilmsBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun init() {
        vb?.rvFilms?.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        filmsAdapter = FilmsRVAdapter(presenter.filmsListPresenter, GlideImageLoader())
        vb?.rvFilms?.adapter = filmsAdapter

        vb?.rvGenres?.layoutManager = LinearLayoutManager(context)
        genresAdapter = GenresRVAdapter(presenter.genresListPresenter)
        vb?.rvGenres?.adapter = genresAdapter

    }

    override fun setTitle(text: String) {
        activity?.title = text
    }

    override fun setHomeButton() {
        val activity = activity as AppCompatActivity
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateFilms() {
        filmsAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateGenres() {
        vb?.rvGenres?.let {
            if (!it.isComputingLayout && it.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                genresAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun setAlert(text: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(text)
            .setPositiveButton(android.R.string.cancel) { _, _ ->
                backPressed()
            }
            .show()
    }

    override fun backPressed() = presenter.backPressed()
}