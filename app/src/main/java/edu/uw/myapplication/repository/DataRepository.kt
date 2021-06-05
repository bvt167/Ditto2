package edu.uw.myapplication.repository

import edu.uw.myapplication.`interface`.PokemonService
import edu.uw.myapplication.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Base URL:
 * https://pokeapi.co/api/v2/
 */

const val DEFAULT_NUM_POKEMON_LIMIT = 898

class DataRepository {

    private val pokemonService = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokemonService::class.java)

    suspend fun getPokemon(name: String): Pokemon = pokemonService.getPokemon(name)
    suspend fun getPokemonSpecies(name: String): PokemonSpecies = pokemonService.getPokemonSpecies(name)
    suspend fun getPokemonList(numPokemonLimit: Int = DEFAULT_NUM_POKEMON_LIMIT): PokemonList = pokemonService.getPokemonList(numPokemonLimit)
    suspend fun getEvolutionChain(id: Int): EvolutionChain = pokemonService.getEvolutionChain(id)
    suspend fun getMove(id: Int): Move = pokemonService.getMove(id)

}