package com.cuvva.findyourdog.feature

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cuvva.findyourdog.feature.detail.BreedDetailViewModel
import com.cuvva.findyourdog.feature.detail.item.BreedDetailItemViewModel
import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.domain.usecase.GetSubBreedImageUrlUseCase
import com.cuvva.library_dogapi.remote.base.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedDetailViewModelTest : ViewModelTest() {

    companion object {
        private val getSubBreedImageUrlUseCaseMock = mockk<GetSubBreedImageUrlUseCase>()
    }

    private val viewModelTest = BreedDetailViewModel(getSubBreedImageUrlUseCaseMock)

    private fun setUpGetSubBreedImageUrlUseCase(imageUrl: String = "") {
        coEvery {
            getSubBreedImageUrlUseCaseMock.invoke(any(), any())
        } coAnswers {
            Result.Success(imageUrl)
        }
    }

    @Before
    fun setUp() {
        clearAllMocks()
        setUpGetSubBreedImageUrlUseCase()
    }

    @Test
    fun onStart() {
        val breed = Breed(
            name = "breed one",
            image = "breed image url",
            subBreeds = listOf(
                Breed.SubBreed(name = "sub breed one"),
                Breed.SubBreed(name = "sub breed two")
            )
        )
        viewModelTest.setArgs(breed)

        setUpGetSubBreedImageUrlUseCase("sub breed image url")
        viewModelTest.onStart()

        assertAll {

            coVerify(exactly = 1) {
                getSubBreedImageUrlUseCaseMock.invoke(
                    breedName = "breed one",
                    subBreedName = "sub breed one"
                )
            }
            coVerify(exactly = 1) {
                getSubBreedImageUrlUseCaseMock.invoke(
                    breedName = "breed one",
                    subBreedName = "sub breed two"
                )
            }

            assertThat(viewModelTest.listItems.value!!.size).isEqualTo(3)
            val listItems = viewModelTest.listItems.value!!
            assertThat((listItems[0] as BreedDetailItemViewModel).name).isEqualTo("breed one")
            assertThat((listItems[0] as BreedDetailItemViewModel).imageUrl).isEqualTo("breed image url")

            assertThat((listItems[1] as BreedDetailItemViewModel).name).isEqualTo("sub breed one")
            assertThat((listItems[1] as BreedDetailItemViewModel).imageUrl).isEqualTo("sub breed image url")

            assertThat((listItems[2] as BreedDetailItemViewModel).name).isEqualTo("sub breed two")
            assertThat((listItems[2] as BreedDetailItemViewModel).imageUrl).isEqualTo("sub breed image url")
        }
    }
}