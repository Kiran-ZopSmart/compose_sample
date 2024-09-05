package com.example.composesample.model

// Represents the result of the API call
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

data class PokemonInfo(
    val pokemonInfo: PokemonListResponse
)