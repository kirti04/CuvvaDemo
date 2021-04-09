package com.cuvva.findyourdog.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cuvva.findyourdog.feature.common.NoDataItemViewModel
import com.cuvva.findyourdog.feature.common.RecyclerViewAdapter.ItemViewModel
import com.cuvva.findyourdog.feature.list.item.BreedListItemViewModel
import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.domain.usecase.GetAllBreedsUseCase
import com.cuvva.library_dogapi.extension.abortOnError
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreedListViewModel @Inject constructor(
    private val getAllBreedsUseCase: GetAllBreedsUseCase
) : ViewModel() {

    var hasInitialized = false
    val event = MutableLiveData<BreedListEvent>()

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listItems = MutableLiveData<List<ItemViewModel>>()
    val listItems: LiveData<List<ItemViewModel>> = _listItems

    private val noDataItem = NoDataItemViewModel()

    val swipeRefreshListener by lazy {
        SwipeRefreshLayout.OnRefreshListener {
            viewModelScope.launch {
                _listItems.value = emptyList()
                _isRefreshing.value = true
                val breeds = loadBreeds()
                updateList(breeds)
                _isRefreshing.value = false
            }
        }
    }

    fun onStart() {
        viewModelScope.launch {
            if (!hasInitialized) {
                loadData()
                hasInitialized = true
            }
        }
    }

    private suspend fun loadData() {
        _isLoading.value = true
        val breeds = loadBreeds()
        updateList(breeds)
        _isLoading.value = false
    }

    private suspend fun loadBreeds() =
        getAllBreedsUseCase.invoke().abortOnError { return emptyList<Breed>() }

    private fun updateList(breeds: List<Breed> = emptyList()) {
        _listItems.value = if (breeds.isEmpty()) {
            listOf(noDataItem)
        } else {
            makeBreedItems(breeds)
        }
    }

    private fun makeBreedItems(breeds: List<Breed>) =
        breeds.map { BreedListItemViewModel(it, ::showBreedDetail) }

    private fun showBreedDetail(breed: Breed) {
        event.value = BreedListEvent.ShowBreedDetail(breed)
    }
}