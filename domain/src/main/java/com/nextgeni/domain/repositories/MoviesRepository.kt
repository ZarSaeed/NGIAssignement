package com.nextgeni.domain.repositories

import com.nextgeni.domain.models.MoviesGetRequest
import com.nextgeni.domain.models.MoviesPaginatedResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMovies(moviesGetRequest: MoviesGetRequest) : Flow<MoviesPaginatedResponse?>
}