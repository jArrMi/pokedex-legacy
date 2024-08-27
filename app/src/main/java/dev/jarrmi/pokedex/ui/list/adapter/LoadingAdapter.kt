package dev.jarrmi.pokedex.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.jarrmi.pokedex.R
import dev.jarrmi.pokedex.databinding.LayoutLoadingItemBinding

class ReposLoadStateViewHolder(
    private val binding: LayoutLoadingItemBinding, retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
       binding.loadingAnimation.run {
            animate().alpha(if (loadState is LoadState.Loading) 1f else 0f).setDuration(300).start()
            isVisible = loadState is LoadState.Loading
        }
        binding.retryButton.run {
            animate().alpha(if (loadState is LoadState.Error) 1f else 0f).setDuration(300).start()
            isVisible = loadState is LoadState.Error
        }
        binding.errorMsg.run {
            animate().alpha(if (loadState is LoadState.Error) 1f else 0f).setDuration(300).start()
            isVisible = loadState is LoadState.Error
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_item, parent, false)
            val binding = LayoutLoadingItemBinding.bind(view)
            return ReposLoadStateViewHolder(binding, retry)
        }
    }
}


class LoadingStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ReposLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder.create(parent, retry)
    }
}

