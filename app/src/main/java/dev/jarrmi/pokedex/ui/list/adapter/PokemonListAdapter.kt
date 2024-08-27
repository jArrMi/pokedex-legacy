package dev.jarrmi.pokedex.ui.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.dispose
import coil.load
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dev.jarrmi.pokedex.R
import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.databinding.LayoutPokemonItemBinding

fun createImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context).run {
        memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25) // Use 25% of the app's available memory
                .build()
        }
        diskCachePolicy(CachePolicy.ENABLED)
        memoryCachePolicy(CachePolicy.ENABLED)
        logger(DebugLogger())
    }.build()
}

class PokemonListAdapter : PagingDataAdapter<Pokemon, PokemonListAdapter.PokemonViewHolder>(
    diffCallback = DiffCallback,
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutPokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onViewRecycled(holder: PokemonViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.itemPokemonImage.dispose()
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }

    class PokemonViewHolder(internal val binding: LayoutPokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.itemPokemonName.text = pokemon.formattedName
            binding.itemPokemonImage.load(
                data = pokemon.listingImageUrl, imageLoader = createImageLoader(itemView.context)
            ) {
                crossfade(true)
                placeholder(R.drawable.pokeball)
                error(R.drawable.missignno)
            }
        }
    }
}
