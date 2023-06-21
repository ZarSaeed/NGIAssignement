package com.nextgeni.presenter.ui.movielisting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextgeni.domain.models.MoviesPaginatedResponse
import com.nextgeni.presenter.R
import com.nextgeni.presenter.ui.components.Loader
import kotlinx.coroutines.launch

@Composable
fun MovieListingScreen(onMovieClick: (Int) -> Unit) {

    val viewModel = hiltViewModel<MovieListingViewModel>()
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }
    val movies = viewModel.moviesList

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == MovieListingViewModel.ListState.IDLE)
            viewModel.getMovies()
    }
    if (viewModel.noDataFound &&
        viewModel.listState != MovieListingViewModel.ListState.LOADING) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.no_data_found))
            Button(onClick = { viewModel.refresh() }) {
                Text(text = stringResource(id = R.string.refresh))
            }
        }
    }
    LazyColumn(state = lazyColumnListState) {
        items(
            items = movies
        ) { movie ->
            MovieItem(movie = movie, onClick = {onMovieClick(movie.id?:0)})
        }
        item (
            key = viewModel.listState,
        ) {
            when(viewModel.listState) {
                MovieListingViewModel.ListState.LOADING -> {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Loader()
                    }
                }
                MovieListingViewModel.ListState.PAGINATING -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Loader()
                    }
                }
                MovieListingViewModel.ListState.PAGINATION_EXHAUST -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            modifier = Modifier
                                .padding(top = 8.dp),
                            elevation = ButtonDefaults.elevation(0.dp),
                            onClick = {
                                coroutineScope.launch {
                                    lazyColumnListState.animateScrollToItem(0)
                                }
                            },
                            content = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowUp,
                                        contentDescription = ""
                                    )

                                    Text(text = stringResource(id = R.string.back_to_top))

                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowUp,
                                        contentDescription = ""
                                    )
                                }
                            }
                        )
                    }
                }
                else -> {}
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieItem(movie = MoviesPaginatedResponse.Movie(), onClick = {})
}

