package ru.ponomarevss.sequeniatesttask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.ponomarevss.sequeniatesttask.R
import ru.ponomarevss.sequeniatesttask.databinding.FragmentFilmsBinding
import ru.ponomarevss.sequeniatesttask.mvp.presenter.FilmsPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.FilmsView
import ru.ponomarevss.sequeniatesttask.ui.App
import ru.ponomarevss.sequeniatesttask.ui.BackButtonListener
import ru.ponomarevss.sequeniatesttask.ui.adapter.FilmsRVAdapter
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

    var filmsAdapter: FilmsRVAdapter? = null
    private var vb: FragmentFilmsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFilmsBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvFilms?.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        filmsAdapter = FilmsRVAdapter(presenter.filmsListPresenter, GlideImageLoader())
        vb?.rvFilms?.adapter = filmsAdapter
    }

    override fun update() {
        filmsAdapter?.notifyDataSetChanged()
    }

    override fun addChip(text: String) {
        val chip = layoutInflater.inflate(R.layout.item_genre, vb?.chgGenres, false) as Chip
        chip.text = text
        chip.setOnCheckedChangeListener { buttonView, isChecked -> presenter.chipCheckedChangeListener(buttonView.text.toString(), isChecked) }
        vb?.chgGenres?.addView(chip)
    }

    override fun setTitle(text: String) {
        activity?.title = text
    }

    override fun backPressed() = presenter.backPressed()
}