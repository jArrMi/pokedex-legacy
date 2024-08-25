package dev.jarrmi.pokedex.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.databinding.LayoutPokemonItemBinding

class PokemonListAdapter : PagingDataAdapter<Pokemon, PokemonListAdapter.MyViewHolder>(
    diffCallback = DiffCallback,
) {

    class MyViewHolder(private val binding: LayoutPokemonItemBinding) : RecyclerView.ViewHolder(
        /* itemView = */ binding.root
    ) {
        fun bind(pokemon: Pokemon) {
            binding.itemPokemonName.text = "${pokemon.name}"
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
