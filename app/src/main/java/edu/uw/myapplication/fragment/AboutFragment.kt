package edu.uw.myapplication.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.databinding.FragmentAboutBinding
import edu.uw.myapplication.model.FlavorTextEntry
import edu.uw.myapplication.repository.DataRepository
import kotlinx.coroutines.launch
import kotlin.math.round

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private val safeArgs: AboutFragmentArgs by navArgs()
    private lateinit var application: DittoApplication
    private lateinit var dataRepository: DataRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = context.applicationContext as DittoApplication
        dataRepository = application.dataRepository
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        val pokemon = safeArgs.pokemon

        if (pokemon != null) {
            lifecycleScope.launch {
                val pokemonSpecies = dataRepository.getPokemonSpecies(pokemon.name)
                with(binding) {
                    tvFlavorText.text = getLatestEnglishFlavorText(pokemonSpecies.flavor_text_entries).replace('\n', ' ', true)
                    tvHeight.text = (pokemon.height * 10).toString() + " cm"
                    tvWeight.text = (pokemon.weight / 10.0).round(3).toString() + " kg"
                    tvAbilities.text = capitalizeWords(pokemon.abilities.joinToString { it.ability.name })
                }
            }
        }

        return binding.root
    }

    private fun getLatestEnglishFlavorText(entries: List<FlavorTextEntry> ): String {
        return entries.last { it.language.name == "en" }.flavor_text
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    private fun capitalizeWords(s: String): String = s.split(", ").joinToString { it.replaceFirstChar { it.uppercaseChar() } }
}