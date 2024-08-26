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
        diskCachePolicy(CachePolicy.ENABLED) // Enable disk caching
        memoryCachePolicy(CachePolicy.ENABLED) // Enable memory caching
        logger(DebugLogger())
    }.build()
}

class PokemonListAdapter : PagingDataAdapter<Pokemon, PokemonListAdapter.MyViewHolder>(
    diffCallback = DiffCallback,
) {

    class MyViewHolder(internal val binding: LayoutPokemonItemBinding) :
        RecyclerView.ViewHolder(/* itemView = */ binding.root
        ) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            LayoutPokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = getItem(position)
        if (pokemon != null) {
            holder.bind(pokemon)
        }
    }

    override fun onViewRecycled(holder: MyViewHolder) {
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
}
