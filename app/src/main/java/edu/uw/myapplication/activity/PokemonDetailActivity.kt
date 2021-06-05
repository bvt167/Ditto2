package edu.uw.myapplication.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.NavGraphDirections
import edu.uw.myapplication.R
import edu.uw.myapplication.adapter.EvolutionAdapter
import edu.uw.myapplication.adapter.TypeAdapter
import edu.uw.myapplication.databinding.ActivityPokemonDetailBinding
import edu.uw.myapplication.model.*
import kotlinx.coroutines.launch

const val POKEMON_NAME_KEY = "POKEMON_NAME_KEY"
private const val EVOLUTION_CHAIN_URL_PREFIX = "https://pokeapi.co/api/v2/evolution-chain/"
private const val MOVE_URL_PREFIX = "https://pokeapi.co/api/v2/move/"

fun navigateToPokemonDetailActivity(context: Context, pokemonName: String) = with(context) {
    val intent = Intent(this, PokemonDetailActivity::class.java).apply {
        val bundle = Bundle().apply {
            putString(POKEMON_NAME_KEY, pokemonName)
        }
        putExtras(bundle)
    }
    startActivity(intent)
}

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding
    private val application by lazy { applicationContext as DittoApplication }
    private val dataRepository by lazy { application.dataRepository }
    private var pokemonName: String = "ditto"
    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityPokemonDetailBinding.inflate(layoutInflater).apply { setContentView(root) }
        intent.getStringExtra(POKEMON_NAME_KEY)?.let {
            pokemonName = it
        }

        loadPokemonData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun loadPokemonData() {
        lifecycleScope.launch {
            val pokemon = dataRepository.getPokemon(pokemonName)
            navController.navigate(NavGraphDirections.actionGlobalAboutFragment(pokemon))
            setFragmentNavigation(pokemon)
            with(binding) {
                pokemon.sprites.other.`official-artwork`?.front_default.let { ivPokemonArt.load(it) }
                tvPokemonName.text = capitalizeWords(pokemon.forms[0].name)
                clContainer.setBackgroundColor(application.getTypeColorByName(pokemon.types.first().type.name))
                rvTypes.layoutManager = LinearLayoutManager(this@PokemonDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                val adapter = TypeAdapter(pokemon.types, application)
                rvTypes.adapter = adapter
            }
        }
    }

    private fun setFragmentNavigation(pokemon: Pokemon) {
        with(binding) {
            rbAbout.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalAboutFragment(pokemon))
            }

            rbStats.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalStatsFragment(pokemon))
            }

            setEvolutionsFragmentNavigation(pokemon)

            rbMoves.setOnClickListener {
                navController.navigate(edu.uw.myapplication.NavGraphDirections.actionGlobalMovesFragment(pokemon))
            }
        }
    }

    private fun setEvolutionsFragmentNavigation(pokemon: Pokemon) {
        with(binding) {
            var listOfPokemon: List<Pokemon> = listOf()
            lifecycleScope.launch {
                val pokemonSpecies = dataRepository.getPokemonSpecies(pokemon.name)
                val evolutionChain = dataRepository.getEvolutionChain(extractIdFromUrl(pokemonSpecies.evolution_chain.url, EVOLUTION_CHAIN_URL_PREFIX))
                listOfPokemon += dataRepository.getPokemon(evolutionChain.chain.species.name)

                evolutionChain.chain.evolves_to.forEach {
                    var currChain = it
                    listOfPokemon += dataRepository.getPokemon(currChain.species.name)
                    while (currChain.evolves_to.isNotEmpty()) {
                        currChain = currChain.evolves_to.first()
                        listOfPokemon += dataRepository.getPokemon(currChain.species.name)
                    }
                }

                rbEvolutions.setOnClickListener {
                    navController.navigate(NavGraphDirections.actionGlobalEvolutionsFragment(pokemon, EvolutionList(listOfPokemon), evolutionChain))
                }
            }
        }
    }

    private fun extractIdFromUrl(url: String, idPrefix: String): Int {
        val size = url.length
        val stringID = url.substring(idPrefix.length, size - 1)
        return stringID.toInt()
    }

    private fun capitalizeWords(s: String): String = s.split(" ").joinToString { it.replaceFirstChar { it.uppercaseChar() } }

}