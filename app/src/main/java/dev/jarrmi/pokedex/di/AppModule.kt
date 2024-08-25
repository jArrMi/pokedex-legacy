package dev.jarrmi.pokedex.di

import dev.jarrmi.pokedex.core.network.PokedexClient
import dev.jarrmi.pokedex.core.network.PokedexService
import dev.jarrmi.pokedex.core.repository.PokemonRepository
import dev.jarrmi.pokedex.ui.list.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokedexService::class.java)
    }
    single { PokedexClient(get()) }
    single { PokemonRepository(get()) }
    viewModel { PokemonListViewModel(get()) }
}
