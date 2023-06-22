package com.nextgeni.presenter.ui.movielisting

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nextgeni.domain.models.MoviesPaginatedResponse
import com.nextgeni.domain.usecases.GetMoviesUC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieListingViewModelTest {

    lateinit var viewModel: MovieListingViewModel
    lateinit var getMoviesUC: GetMoviesUC

    @Before
    fun setup() {
        getMoviesUC = Mockito.mock(GetMoviesUC::class.java)
        viewModel = MovieListingViewModel(getMoviesUC)
    }

    @Test
    fun `getMovies_withPage1_expectingOnly1PageReturn_cannotPaginateFurther`() = runTest {
        //arrange
        `when`(getMoviesUC(viewModel.moviesRequest)).thenReturn(
            flowOf(
                MoviesPaginatedResponse(
                    page = 1,
                    emptyList(), 1, 1
                )
            )
        )
        //act
        viewModel.getMovies()
        //assert
        assert(!viewModel.canPaginate)
    }

}