package com.cuvva.findyourdog.feature.detail.item

import com.cuvva.findyourdog.R
import com.cuvva.findyourdog.feature.common.RecyclerViewAdapter
import com.cuvva.library_dogapi.domain.model.Breed
import java.util.*

internal data class BreedDetailItemViewModel(
    val name: String,
    val imageUrl: String?,
) : RecyclerViewAdapter.ItemViewModel(R.layout.item_breed_detail)