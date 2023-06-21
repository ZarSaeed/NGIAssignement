package com.nextgeni.presenter.ui.movielisting

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nextgeni.presenter.ui.components.Loader
import com.nextgeni.presenter.ui.moviedetails.MovieItem


@Composable
fun MovieListingScreen(uiState: MovieListingViewModel.MoviesListUiState,onMovieClick: () -> Unit) {
    if (uiState.isLoading) Loader()
    LazyColumn {
        items(uiState.movies) {movie ->
            MovieItem(movie = movie)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieListingScreen(MovieListingViewModel.MutableMoviesListUiState(), onMovieClick = {})
}

