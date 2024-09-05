package com.example.composesample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesample.api.PokemonResponse
import com.example.composesample.model.PokemonErrorState
import com.example.composesample.model.PokemonInfo
import com.example.composesample.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) : ViewModel() {
    private val _pokemonList = MutableStateFlow<PokemonViewState>(PokemonViewState.Loading)
    val pokemonList: StateFlow<PokemonViewState> = _pokemonList.asStateFlow()

    fun fetchPokemonData() = viewModelScope.launch {
        val response = pokemonRepository.getPokemonList().first()
        updateUiState(
            when (response) {
                is PokemonResponse.Success -> {
                    PokemonViewState.PokemonView(
                        pokemonInfo = response.pokemonList,
                        errorState = PokemonErrorState(false, "")
                    )
                }
                is PokemonResponse.Error -> {
                    PokemonViewState.PokemonView(
                        pokemonInfo = null,
                        errorState = PokemonErrorState(true, response.errorMessage)
                    )
                }
            }
        )
    }

    private fun updateUiState(newState: PokemonViewState) {
        _pokemonList.update { newState }
    }

    sealed class PokemonViewState {
        /**
         * Represents the loading state when fetching user data.
         */
        data object Loading : PokemonViewState()

        /**
         * Represents the state when user data is available or an error has occurred.
         *
         * @property pokemonInfo The Pokemon information, if available.
         * @property errorState The error state, if an error has occurred.
         * @property isLoading Indicates whether the view is currently loading data.
         */
        data class PokemonView(
            val pokemonInfo: PokemonInfo? = null,
            val errorState: PokemonErrorState,
            val isLoading: Boolean = false
        ) : PokemonViewState()
    }
}