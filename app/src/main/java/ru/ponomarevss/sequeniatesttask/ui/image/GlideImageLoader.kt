package ru.ponomarevss.sequeniatesttask.ui.image

import android.widget.ImageView
import ru.ponomarevss.sequeniatesttask.mvp.model.image.IImageLoader

class GlideImageLoader: IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        GlideApp.with(container.context)
            .load(url)
            .placeholder(null)
            .into(container)
    }
}