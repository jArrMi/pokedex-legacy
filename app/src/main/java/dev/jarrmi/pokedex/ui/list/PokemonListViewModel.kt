package dev.jarrmi.pokedex.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.jarrmi.pokedex.core.model.Pokemon
import dev.jarrmi.pokedex.core.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dev.jarrmi.pokedex.core.network.model.ResultState
import kotlinx.coroutines.flow.Flow

class PokemonListViewModel(private val repository: PokemonRepository) : ViewModel() {

    val pokemonList: Flow<PagingData<Pokemon>> = repository.getPokemonList().cachedIn(viewModelScope)
}
