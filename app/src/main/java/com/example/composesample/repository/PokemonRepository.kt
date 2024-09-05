package com.example.composesample.repository

import com.example.composesample.api.PokemonResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonList(): Flow<PokemonResponse>
}