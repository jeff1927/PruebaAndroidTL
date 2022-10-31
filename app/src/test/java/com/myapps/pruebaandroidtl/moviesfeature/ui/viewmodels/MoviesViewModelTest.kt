package com.myapps.pruebaandroidtl.moviesfeature.ui.viewmodels


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.usecase.GetAllMoviesUseCase
import com.myapps.pruebaandroidtl.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class MoviesViewModelTest{

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var viewModel: MoviesViewModel

    @MockK
    private lateinit var getAllMoviesUseCase: GetAllMoviesUseCase

    @Mock
    private lateinit var movieListObserver: Observer<Resource<List<MovieModel>>>

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setUp(){
        viewModel = MoviesViewModel(getAllMoviesUseCase)
    }


    @Test
    fun `given the dependencies when create the viewModel check is not Null`() {
        viewModel = MoviesViewModel(getAllMoviesUseCase)
        Assert.assertNotNull(viewModel)
    }

    @Test
    fun `given a fake repository when get movies then check if getAllMoviesUseCase was invoked`() = runTest{
        // given
        val mockRepository = FakeRepository()
        coEvery { getAllMoviesUseCase() } returns mockRepository.getMovies()

        // when
        getAllMoviesUseCase()

        // then
        coVerify(exactly = 2) { getAllMoviesUseCase()}
    }

}