package edu.uw.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.databinding.ItemEvolutionBinding
import edu.uw.myapplication.databinding.ItemTypeBinding
import edu.uw.myapplication.model.Chain
import edu.uw.myapplication.model.EvolutionChain
import edu.uw.myapplication.model.EvolutionDetail
import edu.uw.myapplication.model.Pokemon

class EvolutionAdapter(private val pokemon: List<Pokemon>, private val evolutionChain: EvolutionChain, private val application: DittoApplication): RecyclerView.Adapter<EvolutionAdapter.EvolutionListViewHolder>() {

    var onEvolutionClickListener: (pokemon: Pokemon) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionListViewHolder {
        val binding = ItemEvolutionBinding.inflate(LayoutInflater.from(parent.context))
        return EvolutionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EvolutionListViewHolder, position: Int) {
        val currentPokemon = pokemon[position]
        val evolutionDetails = getEvolutionDetails(currentPokemon, evolutionChain)
        with(holder.binding) {
            tvPokemonName.text = currentPokemon.name.replaceFirstChar { it.uppercaseChar() }
            currentPokemon.sprites.front_default.let { ivSprite.load(it) }
            tvMinLevel.text = evolutionDetails.min_level?.toString() ?: "None"
            tvItemReq.text = evolutionDetails.item?.name ?: "None"
            rvTypes.layoutManager = LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
            rvTypes.adapter = TypeAdapter(currentPokemon.types, application)

            root.setOnClickListener { onEvolutionClickListener(currentPokemon) }
        }
    }

    override fun getItemCount(): Int = pokemon.size

    private fun getEvolutionDetails(pokemon: Pokemon, evolutionChain: EvolutionChain): EvolutionDetail {
        evolutionChain.chain.evolves_to.forEach {
            var currChain = it
            if (pokemon.name == currChain.species.name && currChain.evolution_details.isNotEmpty()) {
                return currChain.evolution_details.first()
            } else {
                while(currChain.evolves_to.isNotEmpty()) {
                    currChain = currChain.evolves_to.first()
                    if (pokemon.name == currChain.species.name && currChain.evolution_details.isNotEmpty()) {
                        return currChain.evolution_details.first()
                    }
                }
            }
        }
        return EvolutionDetail(null, null)
    }

    class EvolutionListViewHolder(val binding: ItemEvolutionBinding): RecyclerView.ViewHolder(binding.root)
}