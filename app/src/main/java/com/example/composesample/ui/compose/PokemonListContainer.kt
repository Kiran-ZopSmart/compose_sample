package com.example.composesample.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composesample.handler.OnRetry
import com.example.composesample.viewmodel.MainViewModel.PokemonViewState

@Composable
fun PokemonListContainer(state: PokemonViewState.PokemonView, onRetry: OnRetry) {
    if (state.isLoading) {
        LoadingContainer()
    } else if (state.errorState.isErrorState) {
        ErrorScreen(onRetry = onRetry)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Pokemon Name List",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(1.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.pokemonInfo?.let { pokemonInfo ->
                    items(pokemonInfo.pokemonInfo.results.size) { index ->
                        val pokemon = pokemonInfo.pokemonInfo.results[index]
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f), // This ensures a square aspect ratio
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shadowElevation = 5.dp,
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                            ) {
                                Text(text = pokemon.name)
                                Spacer(modifier = Modifier.height(8.dp))
                                AsyncImage(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxSize()
                                        .padding(top = 8.dp),
                                    model = pokemon.getImageUrl(),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}