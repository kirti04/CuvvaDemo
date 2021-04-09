package com.cuvva.findyourdog.feature.list.item

import com.cuvva.findyourdog.R
import com.cuvva.findyourdog.feature.common.RecyclerViewAdapter.ItemViewModel
import com.cuvva.library_dogapi.domain.model.Breed
import java.util.*

internal data class BreedListItemViewModel(
    private val breed: Breed,
    private val showDetail: (Breed) -> Unit
) : ItemViewModel(R.layout.item_breed_list) {

    val name = "Breed name: ${breed.name.capitalize(Locale.UK)}"
    val image = breed.image

    val listItems = makeSubBreedItem(breed)

    private fun makeSubBreedItem(breed: Breed) = breed.subBreeds.map {
        SubBreedItemViewModel(it)
    }

    fun onClick() {
        showDetail.invoke(breed)
    }
}