package ru.ponomarevss.sequeniatesttask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ponomarevss.sequeniatesttask.databinding.ItemGenreBinding
import ru.ponomarevss.sequeniatesttask.mvp.presenter.list.IGenresListPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.list.GenreItemView

class GenresRVAdapter(
    val presenter: IGenresListPresenter,
) : RecyclerView.Adapter<GenresRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(private val vb: ItemGenreBinding) : RecyclerView.ViewHolder(vb.root), GenreItemView {

        override fun setName(text: String) = with(vb) {
            btnGenre.text = text
            btnGenre.textOn = text
            btnGenre.textOff = text
        }

        override fun setState(isChecked: Boolean) = with(vb) {
            btnGenre.isChecked = isChecked
        }

        override fun setListener() = with(vb) {
            btnGenre.setOnClickListener {
            presenter.buttonClicked(pos)
            }
        }

        override var pos = -1
    }
}