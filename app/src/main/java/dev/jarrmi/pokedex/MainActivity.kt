package dev.jarrmi.pokedex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.jarrmi.pokedex.ui.list.PokemonListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import dev.jarrmi.pokedex.core.network.model.ResultState
import dev.jarrmi.pokedex.databinding.ActivityMainBinding
import dev.jarrmi.pokedex.ui.list.adapter.PokemonListAdapter
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PokemonListAdapter
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

        val recyclerView: RecyclerView = findViewById(R.id.mainScreenList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonListAdapter()
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.pokemonList.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}
