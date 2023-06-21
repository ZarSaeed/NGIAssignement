package com.nextgeni.presenter.ui.movielisting

import androidx.compose.runtime.*
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

    val moviesList = mutableStateListOf<MoviesPaginatedResponse.Movie>()

    private var page by mutableStateOf(1)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(ListState.IDLE)
    var noDataFound by mutableStateOf(false)


    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            if (page == 1 || (page != 1 && canPaginate) && listState == ListState.IDLE) {
                listState = if (page == 1) ListState.LOADING else ListState.PAGINATING
                getMoviesUC(MoviesGetRequest("en-US", page)).catch {
                    listState = if (page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST
                    if (page == 1) noDataFound = true
                }.collect {
                    if (page == 1 && (it?.results?.size ?: 0) > 0) noDataFound = false
                    canPaginate = (it?.total_pages?.minus(1)) != page
                    if (page == 1) {
                        moviesList.clear()
                        moviesList.addAll(it?.results!!)
                    } else {
                        moviesList.addAll(it?.results!!)
                    }
                    listState = ListState.IDLE
                    if (canPaginate)
                        page++
                }
            } else {
                listState = if (page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST
            }
        }
    }

    fun refresh() {
        page = 1
        listState = ListState.IDLE
        canPaginate = false
        getMovies()
    }
    enum class ListState {
        IDLE,
        LOADING,
        PAGINATING,
        ERROR,
        PAGINATION_EXHAUST,
    }

    override fun onCleared() {
        page = 1
        listState = ListState.IDLE
        canPaginate = false
        super.onCleared()
    }

}