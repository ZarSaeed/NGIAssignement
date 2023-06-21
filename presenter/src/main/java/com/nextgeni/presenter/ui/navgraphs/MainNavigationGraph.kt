package com.nextgeni.presenter.ui.navgraphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nextgeni.presenter.ui.moviedetails.MovieDetailsScreen
import com.nextgeni.presenter.ui.moviedetails.MovieDetailsViewModel
import com.nextgeni.presenter.ui.movielisting.MovieListingScreen
import com.nextgeni.presenter.ui.movielisting.MovieListingViewModel

@Composable
fun MainNavGraph(modifier: Modifier = Modifier,
                 navController: NavHostController = rememberNavController(),
                 startDestination: String = "movies") {
        NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
            composable("movies") {
                val viewModel: MovieListingViewModel = hiltViewModel()
                MovieListingScreen ( uiState = viewModel.uiState, onMovieClick = {navController.navigate("movie_details")})
            }
            composable("movie_details") {
                val viewModel: MovieDetailsViewModel = hiltViewModel()
                MovieDetailsScreen(onBack = {navController.popBackStack()})
            }
        }
}