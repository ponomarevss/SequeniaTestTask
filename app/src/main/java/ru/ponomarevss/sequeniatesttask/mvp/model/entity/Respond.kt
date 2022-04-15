package ru.ponomarevss.sequeniatesttask.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Respond(
    @Expose val films: List<Film>
    ): Parcelable
