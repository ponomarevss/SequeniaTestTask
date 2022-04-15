package ru.ponomarevss.sequeniatesttask.mvp.model.navigation

import com.github.terrakok.cicerone.Screen
import ru.ponomarevss.sequeniatesttask.mvp.model.entity.Film

interface IScreens {
    fun films(): Screen
    fun film(film: Film): Screen
}