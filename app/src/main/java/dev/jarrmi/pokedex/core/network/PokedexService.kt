package dev.jarrmi.pokedex.core.network

import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.core.network.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int = 0,
    ): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun fetchPokemonDetail(
        @Path("name") name: String,
    ): Pokemon
}
