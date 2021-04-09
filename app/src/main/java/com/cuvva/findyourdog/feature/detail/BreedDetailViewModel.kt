package com.cuvva.findyourdog.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuvva.findyourdog.feature.common.RecyclerViewAdapter
import com.cuvva.findyourdog.feature.detail.item.BreedDetailItemViewModel
import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.domain.usecase.GetSubBreedImageUrlUseCase
import com.cuvva.library_dogapi.extension.abortOnError
import com.cuvva.library_dogapi.extension.mapSuccess
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreedDetailViewModel @Inject constructor(
    private val getSubBreedImageUrlUseCase: GetSubBreedImageUrlUseCase
) : ViewModel() {

    private lateinit var breed: Breed

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listItems = MutableLiveData<List<RecyclerViewAdapter.ItemViewModel>>()
    val listItems: LiveData<List<RecyclerViewAdapter.ItemViewModel>> = _listItems

    fun setArgs(breed: Breed?) {
        this.breed = breed ?: return
    }

    fun onStart() {
        viewModelScope.launch {
            val subBreedWithImages = breed.subBreeds.map { subBreed ->
                val imageUrl = loadSubBreedImages(subBreed)
                subBreed.copy(image = imageUrl)
            }

            val breed = breed.copy(subBreeds = subBreedWithImages)
            updateListItems(breed)
        }
    }

    private fun updateListItems(breed: Breed) {
        _listItems.value = mutableListOf<RecyclerViewAdapter.ItemViewModel>().apply {
            add(BreedDetailItemViewModel(breed.name, breed.image))
            addAll(makeSubBreedItems(breed))
        }
    }

    private suspend fun loadSubBreedImages(subBreed: Breed.SubBreed): String? =
        getSubBreedImageUrlUseCase.invoke(breed.name, subBreed.name)
            .abortOnError { return null }

    private fun makeSubBreedItems(breed: Breed) = breed.subBreeds.map {
        BreedDetailItemViewModel(it.name, it.image)
    }
}