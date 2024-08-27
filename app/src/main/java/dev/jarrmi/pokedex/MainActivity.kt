package dev.jarrmi.pokedex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jarrmi.pokedex.databinding.ActivityMainBinding
import dev.jarrmi.pokedex.ui.list.PokemonListViewModel
import dev.jarrmi.pokedex.ui.list.adapter.LoadingStateAdapter
import dev.jarrmi.pokedex.ui.list.adapter.PokemonListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: PokemonListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pokemonAdapter = PokemonListAdapter()
        binding.mainScreenList.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = pokemonAdapter.withLoadStateFooter(LoadingStateAdapter(pokemonAdapter::retry))
        }

        lifecycleScope.launch {
            viewModel.pokemonList.collectLatest { pagingData ->
                pokemonAdapter.submitData(pagingData)
            }
        }
    }
}
