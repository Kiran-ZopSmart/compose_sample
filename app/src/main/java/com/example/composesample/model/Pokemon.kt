package com.example.composesample.model

// Represents each Pokemon in the results list
data class Pokemon(
    val name: String,
    val url: String
) {
    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png"
    }
}

data class PokemonErrorState(
    val isErrorState: Boolean,
    val errorString: String,
)

