package com.nextgeni.domain.usecases

import com.nextgeni.domain.models.MovieDetailsRequest
import com.nextgeni.domain.models.MovieDetailsResponse
import com.nextgeni.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUC @Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<MovieDetailsResponse?,MovieDetailsRequest>() {

    override suspend fun run(params: MovieDetailsRequest): Flow<MovieDetailsResponse?> = moviesRepository.getMovieDetails(params)
}