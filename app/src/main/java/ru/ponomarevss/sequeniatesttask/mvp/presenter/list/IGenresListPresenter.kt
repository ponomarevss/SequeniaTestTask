package ru.ponomarevss.sequeniatesttask.mvp.presenter.list

import ru.ponomarevss.sequeniatesttask.mvp.view.list.GenreItemView

interface IGenresListPresenter: IListPresenter<GenreItemView> {
    fun buttonClicked(pos: Int)
}
