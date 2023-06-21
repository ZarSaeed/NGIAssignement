package com.nextgeni.presenter.ui.movielisting

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgeni.domain.models.MoviesGetRequest
import com.nextgeni.domain.models.MoviesPaginatedResponse
import com.nextgeni.domain.usecases.GetMoviesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListingViewModel @Inject constructor(val getMoviesUC: GetMoviesUC) : ViewModel() {

    private val TAG = "MovieListingViewModel"
    private val _uiState = MutableMoviesListUiState()
    val uiState: MoviesListUiState = _uiState

    init {
        getMovies()
    }

    private fun getMovies(){
        viewModelScope.launch {
            _uiState.isLoading = true
            getMoviesUC(MoviesGetRequest("en-US",1)).catch {
                _uiState.isLoading = false
                Log.e(TAG,"exp ${it.localizedMessage}")
            }.collect {
                _uiState.isLoading = false
                it?.results.let { response ->
                    _uiState.movies = response ?: emptyList()
                }
            }
        }
    }


    @Stable
    interface MoviesListUiState {
        val movies: List<MoviesPaginatedResponse.Movie>
        val isLoading: Boolean
    }

    class MutableMoviesListUiState : MoviesListUiState {
        override var movies: List<MoviesPaginatedResponse.Movie> by mutableStateOf(emptyList())
        override var isLoading: Boolean by mutableStateOf(false)
    }
}