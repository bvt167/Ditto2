package edu.uw.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.uw.myapplication.databinding.FragmentAboutBinding
import edu.uw.myapplication.databinding.FragmentStatsBinding
import edu.uw.myapplication.model.Pokemon

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private val safeArgs: AboutFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStatsBinding.inflate(layoutInflater)
        val pokemon = safeArgs.pokemon

        pokemon?.let { setPokemonStats(it) }

        return binding.root
    }

    private fun setPokemonStats(pokemon: Pokemon) {
        var baseStatTotal = 0
        with(binding) {
            pbHp.progress = pokemon.stats.first { it.stat.name == "hp" }.base_stat
            tvHp.text = pbHp.progress.toString()
            baseStatTotal += pbHp.progress
            pbAttack.progress = pokemon.stats.first { it.stat.name == "attack" }.base_stat
            tvAttack.text = pbAttack.progress.toString()
            baseStatTotal += pbAttack.progress
            pbDefense.progress = pokemon.stats.first { it.stat.name == "defense" }.base_stat
            tvDefense.text = pbDefense.progress.toString()
            baseStatTotal += pbDefense.progress
            pbSpecialAttack.progress = pokemon.stats.first { it.stat.name == "special-attack" }.base_stat
            tvSpecialAttack.text = pbSpecialAttack.progress.toString()
            baseStatTotal += pbSpecialAttack.progress
            pbSpecialDefense.progress = pokemon.stats.first { it.stat.name == "special-defense" }.base_stat
            tvSpecialDefense.text = pbSpecialDefense.progress.toString()
            baseStatTotal += pbSpecialDefense.progress
            pbSpeed.progress = pokemon.stats.first { it.stat.name == "speed" }.base_stat
            tvSpeed.text = pbSpeed.progress.toString()
            baseStatTotal += pbSpeed.progress
            tvBaseStatTotal.text = baseStatTotal.toString()
        }
    }

}