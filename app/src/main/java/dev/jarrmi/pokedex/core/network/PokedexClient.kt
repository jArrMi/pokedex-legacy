package dev.jarrmi.pokedex.core.network

import android.util.Log
import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.core.network.model.Details
import dev.jarrmi.pokedex.core.network.model.ResultState
import retrofit2.HttpException
import java.io.IOException

class PokedexClient(
    private val service: PokedexService,
) {

    companion object {
        const val DEFAULT_LIMIT = 25
        private const val TAG = "PokedexClient"
    }

    suspend fun fetchPokemonList(
        offset: Int,
        limit: Int = DEFAULT_LIMIT
    ): ResultState<List<Pokemon>> {
        Log.d(TAG, "fetchPokemonList: offset=$offset, limit=$limit")
        return try {
            val response = service.fetchPokemonList(limit = limit, offset = offset)
            Log.d(TAG, "fetchPokemonList: raw response=${response}")
            Log.d(TAG, "fetchPokemonList: fetched ${response.results.size} items")
            ResultState.Success(response.results)
        } catch (e: HttpException) {
            Log.e(TAG, "fetchPokemonList: HttpException", e)
            ResultState.Error(Details(e.code(), e.message()))
        } catch (e: IOException) {
            Log.e(TAG, "fetchPokemonList: IOException", e)
            ResultState.Error(Details(-1, e.message))
        }
    }

    suspend fun fetchPokemonDetail(name: String): ResultState<Pokemon> {
        Log.d(TAG, "fetchPokemonDetail: name=$name")
        return try {
            val response = service.fetchPokemonDetail(name)
            Log.d(TAG, "fetchPokemonDetail: raw response=${response}")
            Log.d(TAG, "fetchPokemonDetail: fetched details for $name")
            ResultState.Success(response)
        } catch (e: HttpException) {
            Log.e(TAG, "fetchPokemonDetail: HttpException", e)
            ResultState.Error(Details(e.code(), e.message()))
        } catch (e: IOException) {
            Log.e(TAG, "fetchPokemonDetail: IOException", e)
            ResultState.Error(Details(-1, e.message))
        }
    }
}
