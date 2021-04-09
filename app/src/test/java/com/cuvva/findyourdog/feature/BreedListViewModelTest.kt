package com.cuvva.findyourdog.feature

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import com.cuvva.findyourdog.feature.common.NoDataItemViewModel
import com.cuvva.findyourdog.feature.list.BreedListViewModel
import com.cuvva.findyourdog.feature.list.item.BreedListItemViewModel
import com.cuvva.findyourdog.feature.list.item.SubBreedItemViewModel
import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.domain.usecase.GetAllBreedsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test
import com.cuvva.library_dogapi.remote.base.Result
import io.mockk.clearAllMocks
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

@ExperimentalCoroutinesApi
class BreedListViewModelTest : ViewModelTest() {

    companion object {
        private val getAllBreedsUseCaseMock = mockk<GetAllBreedsUseCase>()
    }

    private val viewModelTest = BreedListViewModel(getAllBreedsUseCaseMock)

    private fun setUpGetAllBreedsUseCase(result: List<Breed> = emptyList()) {
        coEvery {
            getAllBreedsUseCaseMock.invoke()
        } coAnswers {
            Result.Success(result)
        }
    }

    @Before
    fun setUp() {
        clearAllMocks()
        setUpGetAllBreedsUseCase()
    }

    @Test
    fun onStart() {
        val breed1 = Breed(
            name = "breed one",
            image = null,
            subBreeds = listOf(Breed.SubBreed(name = "sub breed one"))
        )
        val breed2 = Breed(
            name = "breed two",
            image = null,
            subBreeds = listOf(Breed.SubBreed(name = "sub breed two"))
        )
        setUpGetAllBreedsUseCase(result = listOf(breed1, breed2))

        viewModelTest.onStart()

        assertAll {
            coVerify(exactly = 1) { getAllBreedsUseCaseMock.invoke() }

            assertThat(viewModelTest.listItems.value!!.size).isEqualTo(2)
            assertThat(viewModelTest.listItems.value!![0]).isInstanceOf(BreedListItemViewModel::class)
            assertThat(viewModelTest.listItems.value!![1]).isInstanceOf(BreedListItemViewModel::class)

            val breedItemOne = viewModelTest.listItems.value!![0] as BreedListItemViewModel
            assertThat(breedItemOne.name).isEqualTo("Breed name: Breed one")
            assertThat(breedItemOne.image).isNull()
            assertThat(breedItemOne.listItems.size).isEqualTo(1)
            assertThat((breedItemOne.listItems[0] as SubBreedItemViewModel).subBreedName).isEqualTo(
                "Sub breed name: Sub breed one"
            )

            val breedItemTwo = viewModelTest.listItems.value!![1] as BreedListItemViewModel
            assertThat(breedItemTwo.name).isEqualTo("Breed name: Breed two")
            assertThat(breedItemTwo.image).isNull()
            assertThat(breedItemTwo.listItems.size).isEqualTo(1)
            assertThat((breedItemTwo.listItems[0] as SubBreedItemViewModel).subBreedName).isEqualTo(
                "Sub breed name: Sub breed two"
            )
        }
    }

    @Test
    fun onStart_withGetAllBreedUseCaseError() {
        coEvery {
            getAllBreedsUseCaseMock.invoke()
        } coAnswers {
            Result.Error("error")
        }

        viewModelTest.onStart()

        assertAll {
            assertThat(viewModelTest.listItems.value!!.size).isEqualTo(1)
            assertThat(viewModelTest.listItems.value!![0]).isInstanceOf(NoDataItemViewModel::class.java)
        }
    }

    @Test
    fun onRefresh() {
        viewModelTest.swipeRefreshListener.onRefresh()
        coVerify(exactly = 1) { getAllBreedsUseCaseMock.invoke() }
    }

    @Test
    fun noData() {
        viewModelTest.onStart()
        assertAll {
            assertThat(viewModelTest.listItems.value!!.size).isEqualTo(1)
            assertThat(viewModelTest.listItems.value!![0]).isInstanceOf(NoDataItemViewModel::class.java)
        }
    }
}