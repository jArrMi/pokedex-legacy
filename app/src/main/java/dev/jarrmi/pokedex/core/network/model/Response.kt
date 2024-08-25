package dev.jarrmi.pokedex.core.network.model

import dev.jarrmi.pokedex.core.model.Pokemon

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>,
)
