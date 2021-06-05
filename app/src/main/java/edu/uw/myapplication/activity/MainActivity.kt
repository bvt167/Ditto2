package edu.uw.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val application by lazy { applicationContext as DittoApplication }
    private val dataRepository by lazy { application.dataRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.btnRandomizePokemon.setOnClickListener { setPokemonDetailNavigation() }
        setPokemonDetailNavigation()

    }

    private fun setPokemonDetailNavigation() {
        with(binding) {
            lifecycleScope.launch {
                val randomPokemon = dataRepository.getPokemonList().results.random().name
                binding.btnNavigatePokemonDetail.setOnClickListener {
                    navigateToPokemonDetailActivity(this@MainActivity, randomPokemon)
                }
            }
        }
    }
}