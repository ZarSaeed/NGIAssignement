package com.nextgeni.presenter.ui.moviedetails

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgeni.domain.models.MovieDetailsRequest
import com.nextgeni.domain.models.MovieDetailsResponse
import com.nextgeni.domain.usecases.GetMovieDetailsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(val getMovieDetailsUC: GetMovieDetailsUC): ViewModel() {

    private val TAG = "MovieDetailsViewModel"
    val movieDetailsRequest = MovieDetailsRequest()

    private val _uiState = MutableUiState()
    val uiState: MovieDetailsUiState = _uiState
    fun handleArguments(bundle: Bundle?) {
        movieDetailsRequest.movieId = bundle?.getString("movie_id") ?: "-1"
        getMovieDetails()
    }
    fun getMovieDetails() {
        viewModelScope.launch {
            getMovieDetailsUC(movieDetailsRequest).catch {
                Log.e(TAG,"failed")
                _uiState.noDataFound = true
            }.collect {
                _uiState.noDataFound = false
                _uiState.movieDetails = it
            }
        }
    }

    class MutableUiState : MovieDetailsUiState {
        override var noDataFound: Boolean by mutableStateOf(false)
        override var movieDetails: MovieDetailsResponse? by
        mutableStateOf(null)
    }

    @Stable
    interface MovieDetailsUiState {
        val noDataFound: Boolean
        val movieDetails: MovieDetailsResponse?
    }

}