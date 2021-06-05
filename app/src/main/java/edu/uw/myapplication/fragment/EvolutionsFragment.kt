package edu.uw.myapplication.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.activity.navigateToPokemonDetailActivity
import edu.uw.myapplication.adapter.EvolutionAdapter
import edu.uw.myapplication.databinding.FragmentEvolutionsBinding
import edu.uw.myapplication.databinding.FragmentStatsBinding
import edu.uw.myapplication.model.Pokemon
import edu.uw.myapplication.repository.DataRepository
import kotlinx.coroutines.launch

class EvolutionsFragment : Fragment() {

    private lateinit var binding: FragmentEvolutionsBinding
    private val safeArgs: EvolutionsFragmentArgs by navArgs()
    private lateinit var application: DittoApplication
    private lateinit var dataRepository: DataRepository
    private lateinit var activityContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = context.applicationContext as DittoApplication
        dataRepository = application.dataRepository
        activityContext = context
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEvolutionsBinding.inflate(layoutInflater)

        with(binding) {
            val adapter = EvolutionAdapter(safeArgs.evolutionList.listOfPokemon, safeArgs.evolutionChain, application)
            rvEvolutions.adapter = adapter
            adapter.onEvolutionClickListener = { pokemon: Pokemon ->
                navigateToPokemonDetailActivity(activityContext, pokemon.name)
            }
        }

        return binding.root
    }

}