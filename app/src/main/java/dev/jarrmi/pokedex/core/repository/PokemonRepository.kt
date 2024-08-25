package dev.jarrmi.pokedex.core.repository

import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.core.network.PokedexClient
import dev.jarrmi.pokedex.core.network.model.ResultState

class PokemonRepository(private val client: PokedexClient) {

    suspend fun getPokemonList(page: Int, limit: Int = PokedexClient.DEFAULT_LIMIT): ResultState<List<Pokemon>> {
        return client.fetchPokemonList(page, limit)
    }

    suspend fun getPokemonDetail(name: String): ResultState<Pokemon> {
        return client.fetchPokemonDetail(name)
    }
}
