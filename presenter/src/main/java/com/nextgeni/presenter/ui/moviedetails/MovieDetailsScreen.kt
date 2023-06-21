package com.nextgeni.presenter.ui.moviedetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nextgeni.presenter.BuildConfig
import com.nextgeni.presenter.R

@Composable
fun MovieDetailsScreen(onBack: () -> Unit,
                       refresh: () -> Unit,
                       uiState: MovieDetailsViewModel.MovieDetailsUiState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(uiState.noDataFound) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.no_data_found))
                Button(onClick = {
                    refresh()
                }) {
                    Text(text = stringResource(id = R.string.refresh))
                }
            }
        } else {
            AsyncImage(
                model = "${BuildConfig.IMAGE_URL}${uiState.movieDetails?.backdrop_path}",
                contentDescription = "Movie Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    text = uiState.movieDetails?.title ?: "",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Release Date: ${uiState.movieDetails?.release_date}",
                    style = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                val genreNames = mutableListOf<String>()
                uiState.movieDetails?.genres?.forEach {
                    genreNames.add(it.name ?: "")
                }
                Text(text = "Genre: ")
                LazyRow {
                    items(items = genreNames) {
                        Text(
                            text = "$it |",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Description: ${uiState.movieDetails?.overview}",
                    style = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                val companies = uiState.movieDetails?.production_companies
                val companiesNames = mutableListOf<String>()
                companies?.forEach {
                    companiesNames.add(it.name ?: "")
                }
                Text(text = "Production Companies: ", style = TextStyle(fontSize = 14.sp))
                LazyRow {
                    items(
                        items = companiesNames
                    ) { company ->
                        Text(
                            text = "$company |",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                val languageNames = mutableListOf<String>()
                uiState.movieDetails?.spoken_languages?.forEach {
                    languageNames.add(it.name?:"")
                }
                Text(
                    text = "Language: ",
                    style = TextStyle(fontSize = 14.sp)
                )
                LazyRow {
                    items(languageNames) {
                        Text(
                            text = "$it |",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieDetailsScreen(onBack = {  }, uiState = MovieDetailsViewModel.MutableUiState(), refresh = {})
}