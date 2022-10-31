package com.myapps.pruebaandroidtl.moviesfeature.domain.usecase

import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import com.myapps.pruebaandroidtl.moviesfeature.ui.viewmodels.FakeRepository
import com.myapps.pruebaandroidtl.moviesfeature.ui.viewmodels.mockMoviesList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAllMoviesUseCaseTest() {

    @MockK
    private lateinit var getAllMoviesUseCase: GetAllMoviesUseCase

    @MockK
    private lateinit var repository: MoviesRepository

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setup() {
        getAllMoviesUseCase = GetAllMoviesUseCase(repository)
    }


    @Test
    fun `given the dependencies when create getAllMoviesDataSource check is not Null`() {
        getAllMoviesUseCase = GetAllMoviesUseCase(repository)
        Assert.assertNotNull(getAllMoviesUseCase)
    }

    @Test
    fun `given a fake repository when invoke getAllMoviesUseCase then check if getAllMoviesUseCase was invoked`() =
        runTest {
            // given
            val fakeRepository = FakeRepository()
            coEvery { repository.getMovies() } returns fakeRepository.getMovies()

            // when
            getAllMoviesUseCase.invoke()

            // then
            coVerify(exactly = 1) { getAllMoviesUseCase() }
        }

    @Test
    fun `given a fake repository when collect getAllMoviesUseCase then check if collected data is equals to fake movies list`() =
        runTest {
            // given
            val fakeRepository = FakeRepository()
            coEvery { repository.getMovies() } returns fakeRepository.getMovies()

            // when
            getAllMoviesUseCase().collect {

                // then
                assert(it.data == mockMoviesList)
            }
        }

}