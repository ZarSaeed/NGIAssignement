package com.nextgeni.presenter.ui.navgraphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nextgeni.presenter.ui.moviedetails.MovieDetailsScreen
import com.nextgeni.presenter.ui.moviedetails.MovieDetailsViewModel
import com.nextgeni.presenter.ui.movielisting.MovieListingScreen

@Composable
fun MainNavGraph(modifier: Modifier = Modifier,
                 navController: NavHostController = rememberNavController(),
                 startDestination: String = "movies") {
        NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
            composable("movies") {
                MovieListingScreen (onMovieClick = { movieId ->
                    navController.navigate("movie_details/$movieId")
                })
            }
            composable("movie_details/{movie_id}", arguments = listOf(
                navArgument("movie_id") { type = NavType.StringType }
            )) { backStackEntry ->
                val viewModel: MovieDetailsViewModel = hiltViewModel()
                viewModel.handleArguments(backStackEntry.arguments)
                MovieDetailsScreen(onBack = {navController.popBackStack()},
                    uiState = viewModel.uiState,
                    refresh = {
                        viewModel.getMovieDetails()
                    })
            }
        }
}