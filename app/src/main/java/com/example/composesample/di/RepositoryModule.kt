package com.example.composesample.di

import com.example.composesample.repository.PokemonRepository
import com.example.composesample.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
}