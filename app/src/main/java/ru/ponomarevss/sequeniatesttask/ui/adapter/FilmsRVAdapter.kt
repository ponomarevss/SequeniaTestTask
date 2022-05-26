package ru.ponomarevss.sequeniatesttask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.ponomarevss.sequeniatesttask.databinding.ItemFilmBinding
import ru.ponomarevss.sequeniatesttask.mvp.model.image.IImageLoader
import ru.ponomarevss.sequeniatesttask.mvp.presenter.list.IFilmsListPresenter
import ru.ponomarevss.sequeniatesttask.mvp.view.list.FilmItemView

class FilmsRVAdapter(
    val presenter: IFilmsListPresenter,
    val imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<FilmsRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(private val vb: ItemFilmBinding) : RecyclerView.ViewHolder(vb.root), FilmItemView {

        override fun setName(text: String) = with(vb) { tvFilm.text = text }

        override fun loadImage(url: String?): Unit = with(vb) {
            url?.let { imageLoader.loadInto(url, ivFilm) } ?: ivFilm.setImageDrawable(null)
        }

        override var pos = -1
    }
}