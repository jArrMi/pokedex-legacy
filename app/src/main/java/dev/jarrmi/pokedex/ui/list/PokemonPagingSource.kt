package dev.jarrmi.pokedex.ui.list

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.core.network.PokedexClient
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.max

class PokemonPagingSource(
    private val client: PokedexClient,
) : PagingSource<Int, Pokemon>() {

    private companion object {
        private const val STARTING_PAGE_INDEX = 0
        private const val TAG = "PokemonPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        val anchorPosition = state.anchorPosition
        val refreshKey = state.run {
            anchorPosition?.let {
                closestPageToPosition(it)?.prevKey?.plus(1)
                    ?: closestPageToPosition(it)?.nextKey?.minus(1)
            }
        }
        Log.d(TAG, "getRefreshKey: anchorPosition=$anchorPosition, refreshKey=$refreshKey")
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key ?: STARTING_PAGE_INDEX
        Log.d(TAG, "load: key=$position, loadSize=${params.loadSize}")

        return try {
            val response = client.fetchPokemonList(
                offset = position,
                limit = params.loadSize,
            )
            val pokemon = response.data ?: emptyList()
            val nextKey = (position + params.loadSize).takeUnless { pokemon.isEmpty() }
            Log.d(TAG, "load: fetched ${pokemon.size} items, nextKey=$nextKey")

            LoadResult.Page(
                data = pokemon,
                prevKey = (position - 1).takeUnless { position == STARTING_PAGE_INDEX },
                nextKey = nextKey,
            )
        } catch (exception: IOException) {
            Log.e(TAG, "load: IOException", exception)
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "load: HttpException", exception)
            LoadResult.Error(exception)
        }
    }

    private fun ensureValidIndex(newIndex: Int) = max(STARTING_PAGE_INDEX, newIndex)
}
