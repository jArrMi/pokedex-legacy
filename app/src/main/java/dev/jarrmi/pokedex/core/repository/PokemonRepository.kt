package dev.jarrmi.pokedex.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.core.network.PokedexClient
import dev.jarrmi.pokedex.core.network.model.ResultState
import dev.jarrmi.pokedex.ui.list.PokemonPagingSource
import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val client: PokedexClient) {

    fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = PokedexClient.DEFAULT_LIMIT,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { PokemonPagingSource(client) },
        ).flow
    }

    suspend fun getPokemonDetail(name: String): ResultState<Pokemon> {
        return client.fetchPokemonDetail(name)
    }
}
