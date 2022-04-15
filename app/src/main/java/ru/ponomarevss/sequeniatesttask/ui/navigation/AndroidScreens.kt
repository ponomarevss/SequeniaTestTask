package ru.ponomarevss.sequeniatesttask.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film
import ru.ponomarevss.sequeniatesttask.mvp.model.navigation.IScreens
import ru.ponomarevss.sequeniatesttask.ui.fragment.FilmFragment
import ru.ponomarevss.sequeniatesttask.ui.fragment.FilmsFragment

class AndroidScreens : IScreens {

    override fun films(): Screen = FragmentScreen {FilmsFragment.newInstance()}

    override fun film(film: Film): Screen = FragmentScreen {FilmFragment.newInstance(film)}
}