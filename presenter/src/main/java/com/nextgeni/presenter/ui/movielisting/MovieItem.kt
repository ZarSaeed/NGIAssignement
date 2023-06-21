package com.nextgeni.presenter.ui.movielisting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nextgeni.domain.models.MoviesPaginatedResponse
import com.nextgeni.presenter.BuildConfig


@Composable
fun MovieItem(
    movie: MoviesPaginatedResponse.Movie,
    onClick: () -> Unit
) {
    Box(Modifier.padding(8.dp).clickable {onClick()}) {
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = "${BuildConfig.IMAGE_URL}${movie.poster_path}",
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .size(85.dp)
                        .height(85.dp)
                        .aspectRatio(0.67f)
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = movie.title ?: "Not Fount",
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Release Date: ${movie.release_date}",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = "Genre(s): ${movie.genre_ids?.size}",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = movie.overview ?: "Not Found",
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

