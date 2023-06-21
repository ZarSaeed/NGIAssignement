package com.nextgeni.data.repository.remote

import com.nextgeni.data.network.API
import com.nextgeni.data.network.NetworkCall
import com.nextgeni.domain.models.MovieDetailsRequest
import com.nextgeni.domain.models.MovieDetailsResponse
import com.nextgeni.domain.models.MoviesGetRequest
import com.nextgeni.domain.models.MoviesPaginatedResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val networkCall: NetworkCall) {

    suspend fun getMovies(request: MoviesGetRequest) : Flow<MoviesPaginatedResponse?> {
        val query = HashMap<String, String>().apply {
            put("page", "${request.page}")
            put("language", request.language)
        }
        return networkCall.get<MoviesPaginatedResponse>(API.MOVIES_LIST,query)
    }

    suspend fun getMovieDetails(request: MovieDetailsRequest) : Flow<MovieDetailsResponse?> {
        val query = HashMap<String, String>().apply {
            put("language", request.language)
        }
        return networkCall.get<MovieDetailsResponse>(API.MOVIE_DETAILS.replace(oldValue = "{movie_id}", newValue = request.movieId),query)
    }
}