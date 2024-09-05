package com.example.composesample.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composesample.handler.OnRetry
import com.example.composesample.viewmodel.MainViewModel
import com.example.composesample.viewmodel.MainViewModel.PokemonViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val dataFetched = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        if (!dataFetched.value) {
            viewModel.fetchPokemonData()
            dataFetched.value = true
        }
    }

    val state = viewModel.pokemonList.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Pokemon") }
            )
        }
    ) { innerPadding ->
        MainScreenImpl(
            state = state,
            onRetry = OnRetry(
                retry = {
                    viewModel.retryFetchingPokemonData()
                }
            ),
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
fun MainScreenImpl(
    state: PokemonViewState,
    onRetry: OnRetry,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (state) {
            is PokemonViewState.Loading -> {
                LoadingContainer()
            }
            is PokemonViewState.PokemonView -> {
                PokemonListContainer(state, onRetry)
            }
        }
    }
}