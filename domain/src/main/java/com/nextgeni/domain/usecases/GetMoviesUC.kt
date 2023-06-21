package com.nextgeni.domain.usecases

import com.nextgeni.domain.models.MoviesGetRequest
import com.nextgeni.domain.models.MoviesPaginatedResponse
import com.nextgeni.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUC @Inject constructor(private val getMoviesRepository: MoviesRepository) : UseCase<MoviesPaginatedResponse?,MoviesGetRequest>() {

    override suspend fun run(params: MoviesGetRequest): Flow<MoviesPaginatedResponse?> = getMoviesRepository.getMovies(params)
}