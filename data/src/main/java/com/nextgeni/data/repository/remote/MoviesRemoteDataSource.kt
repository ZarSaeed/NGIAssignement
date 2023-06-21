package com.nextgeni.data.repository.remote

import com.nextgeni.data.network.API
import com.nextgeni.data.network.NetworkCall
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
}