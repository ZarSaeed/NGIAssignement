package com.nextgeni.data.network

import com.nextgeni.data.BuildConfig


object API {
    private const val API = BuildConfig.BASE_URL
    private const val VERSION = "3"
    const val MOVIES_LIST = "$API$VERSION/movie/popular"
    const val MOVIE_DETAILS = "$API$VERSION/movie/{movie_id}"
}