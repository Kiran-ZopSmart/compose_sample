package com.example.composesample.repository

import com.example.composesample.api.PokemonResponse
import com.example.composesample.api.PokemonService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private val apiService: PokemonService): PokemonRepository {

    override suspend fun getPokemonList(): Flow<PokemonResponse> = apiService.getPokemonList()
}