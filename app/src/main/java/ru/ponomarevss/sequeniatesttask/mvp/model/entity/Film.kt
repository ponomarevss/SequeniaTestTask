package ru.ponomarevss.sequeniatesttask.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    @Expose val id: Int,
    @Expose val localizedName: String,
    @Expose val name: String,
    @Expose val year: Int,
    @Expose val rating: Float?,
    @Expose val imageUrl: String?,
    @Expose val description: String?,
    @Expose val genres: List<String>
    ): Parcelable