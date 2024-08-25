package dev.jarrmi.pokedex.core.network

import dev.jarrmi.pokedex.core.model.Pokemon
import retrofit2.HttpException
import dev.jarrmi.pokedex.core.network.model.ResultState
import java.io.IOException

class PokedexClient(
    private val service: PokedexService,
) {

    companion object {
        const val DEFAULT_LIMIT = 15
    }

    suspend fun fetchPokemonList(page: Int, limit: Int = DEFAULT_LIMIT): ResultState<List<Pokemon>> {
        val offset = (page - 1) * limit
        return try {
            val response = service.fetchPokemonList(limit = limit, offset = offset)
            ResultState.Success(response.results)
        } catch (e: HttpException) {
            ResultState.Error(ErrorResponse(e.code(), e.message()))
        } catch (e: IOException) {
            ResultState.Error(ErrorResponse(-1, e.message))
        }
    }

    suspend fun fetchPokemonDetail(name: String): ResultState<Pokemon> {
        return try {
            val response = service.fetchPokemonDetail(name)
            ResultState.Success(response)
        } catch (e: HttpException) {
            ResultState.Error(ErrorResponse(e.code(), e.message()))
        } catch (e: IOException) {
            ResultState.Error(ErrorResponse(-1, e.message))
        }
    }
}
