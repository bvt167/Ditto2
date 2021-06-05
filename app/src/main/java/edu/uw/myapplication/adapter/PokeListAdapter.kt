package edu.uw.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.databinding.ItemPokemonBinding
import edu.uw.myapplication.model.PokemonList
import edu.uw.myapplication.repository.DataRepository
import kotlinx.coroutines.runBlocking

class PokeListAdapter (private val listOfPokemon: PokemonList): RecyclerView.Adapter<PokeListAdapter.PokeListViewHolder>()  {
    private lateinit var application: DittoApplication
    private lateinit var dataRepository: DataRepository

    var onPokemonClickListener: (name: String) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeListViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context))
        application = parent.context.applicationContext as DittoApplication
        dataRepository = application.dataRepository
        return PokeListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfPokemon.count
    }

    override fun onBindViewHolder(holder: PokeListViewHolder, position: Int) {
        val pokeListItem = listOfPokemon.results[position]
        val pokemon = runBlocking { dataRepository.getPokemon(pokeListItem.name) }

        with(holder.binding) {
            // load the pokemon picture
            pokemon.sprites.other.`official-artwork`?.front_default.let { pokePic.load(it) }

            // load the pokemon name
            pokeName.text = pokemon.name
        }
        holder.itemView.setOnClickListener{
            onPokemonClickListener(pokemon.name)
        }
    }
    class PokeListViewHolder(val binding: ItemPokemonBinding): RecyclerView.ViewHolder(binding.root)

}