package com.example.composesample.api

import com.example.composesample.model.PokemonListResponse
import com.example.composesample.model.Pokemon
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface defining the API endpoints for user-related operations.
 */
interface ApiService {
    /**
     * Fetches a list of Pokemon from the API.
     *
     * @return A [PokemonListResponse] object containing a list of [Pokemon] objects.
     */
    @GET("pokemon?limit=100")
    suspend fun getPokemonList(): Response<PokemonListResponse>
}