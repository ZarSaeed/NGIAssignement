package com.nextgeni.data.repository

import com.nextgeni.data.repository.remote.MoviesRemoteDataSource
import com.nextgeni.domain.models.MovieDetailsRequest
import com.nextgeni.domain.models.MovieDetailsResponse
import com.nextgeni.domain.models.MoviesGetRequest
import com.nextgeni.domain.models.MoviesPaginatedResponse
import com.nextgeni.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource): MoviesRepository {

    override suspend fun getMovies(moviesGetRequest: MoviesGetRequest): Flow<MoviesPaginatedResponse?> = moviesRemoteDataSource.getMovies(moviesGetRequest)
    override suspend fun getMovieDetails(movieDetailsRequest: MovieDetailsRequest): Flow<MovieDetailsResponse?> = moviesRemoteDataSource.getMovieDetails(movieDetailsRequest)
}