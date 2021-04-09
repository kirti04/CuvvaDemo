package com.cuvva.library_dogapi.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Breed(
    val name: String,
    val image: String? = null,
    val subBreeds: List<SubBreed> = emptyList()
) : Parcelable {

    @Parcelize
    data class SubBreed(
        val name: String,
        val image: String? = null,
    ) : Parcelable
}