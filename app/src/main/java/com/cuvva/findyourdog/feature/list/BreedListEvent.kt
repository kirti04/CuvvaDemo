package com.cuvva.findyourdog.feature.list

import com.cuvva.library_dogapi.domain.model.Breed

sealed class BreedListEvent {

    class ShowBreedDetail(val breed: Breed) : BreedListEvent()
}