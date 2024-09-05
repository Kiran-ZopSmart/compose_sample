package com.example.composesample.api

import com.example.composesample.model.PokemonInfo
import com.example.composesample.model.PokemonListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PokemonService @Inject constructor(private val apiService: ApiService) {

    suspend fun getPokemonList(): Flow<PokemonResponse> {
        return flow {
            val response = apiService.getPokemonList()
            emit(handleResponse(response))
        }.catch { error ->
            emit(PokemonResponse.Error(error.message.orEmpty()))
        }
    }

    private fun handleResponse(response: Response<PokemonListResponse>): PokemonResponse {
        return when {
            response.isSuccessful -> {
                val pokemonInfo = response.body()
                if (pokemonInfo != null) {
                    PokemonResponse.Success(PokemonInfo(pokemonInfo))
                } else {
                    PokemonResponse.Error("User response body is null")
                }
            }

            else -> {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (!errorBody.isNullOrEmpty()) {
                    errorBody
                } else {
                    response.message() ?: "Unknown error occurred"
                }
                PokemonResponse.Error(errorMessage)
            }
        }
    }

}

sealed class PokemonResponse {
    data class Success(val pokemonList: PokemonInfo) : PokemonResponse()
    data class Error(val errorMessage: String) : PokemonResponse()
}