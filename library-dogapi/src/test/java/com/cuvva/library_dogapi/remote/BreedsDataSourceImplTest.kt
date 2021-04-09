package com.cuvva.library_dogapi.remote

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cuvva.library_dogapi.remote.base.AppResponse
import com.cuvva.library_dogapi.remote.base.Result
import com.cuvva.library_dogapi.remote.base.RetrofitServiceFactory
import com.cuvva.library_dogapi.remote.source.BreedsDataSourceImpl
import com.cuvva.library_dogapi.remote.source.BreedsService
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class BreedsDataSourceImplTest {

    companion object {
        private val breedsServiceMock = mockk<BreedsService>()
        private val retrofitServiceFactoryMock = mockk<RetrofitServiceFactory>()
    }

    private lateinit var sut: BreedsDataSourceImpl

    @Before
    fun setUp() {
        clearAllMocks()
        setUpBreedsService()
        setUpBreedsServiceMockResponse()
        sut = BreedsDataSourceImpl(retrofitServiceFactoryMock)
    }

    private fun setUpBreedsService() {
        every { retrofitServiceFactoryMock.make(BreedsService::class.java) } answers { breedsServiceMock }
    }

    private fun setUpBreedsServiceMockResponse() {
        coEvery {
            breedsServiceMock.getBreeds()
        } coAnswers {
            Response.success(AppResponse(arrayOf("breed")))
        }

        coEvery {
            breedsServiceMock.getBreedImage(any())
        } coAnswers {
            Response.success(AppResponse("breed image url"))
        }

        coEvery {
            breedsServiceMock.getSubBreeds(any())
        } coAnswers {
            Response.success(AppResponse(arrayOf("sub breed name")))
        }
    }

    @Test
    fun getBreeds() = runBlocking {
        val result = sut.getBreeds() as Result.Success

        coVerify(exactly = 1) {
            breedsServiceMock.getBreeds()
            breedsServiceMock.getBreedImage("breed")
            breedsServiceMock.getSubBreeds("breed")
        }

        assertThat(result.value.size).isEqualTo(1)
        assertThat(result.value[0].name).isEqualTo("breed")
        assertThat(result.value[0].image).isEqualTo("breed image url")
        assertThat(result.value[0].subBreeds[0].name).isEqualTo("sub breed name")
    }

    @Test
    fun getSubBreedImageUrl() = runBlocking {
        coEvery {
            breedsServiceMock.getSubBreedImage(any(), any())
        } coAnswers {
            Response.success(AppResponse("sub breed image url"))
        }

        val result = sut.getSubBreedImageUrl(
            breedName = "breed",
            subBreedName = "sub breed"
        ) as Result.Success

        coVerify(exactly = 1) { breedsServiceMock.getSubBreedImage("breed", "sub breed") }
        assertThat(result.value).isEqualTo("sub breed image url")
    }
}