package com.cuvva.findyourdog.feature.list.item

import com.cuvva.findyourdog.R
import com.cuvva.findyourdog.feature.common.RecyclerViewAdapter
import com.cuvva.library_dogapi.domain.model.Breed
import java.util.*

internal data class SubBreedItemViewModel(
    private val subBreed: Breed.SubBreed
) : RecyclerViewAdapter.ItemViewModel(R.layout.item_breed_list_sub_breed) {

    val subBreedName = "Sub breed name: ${subBreed.name.capitalize(Locale.UK)}"
}