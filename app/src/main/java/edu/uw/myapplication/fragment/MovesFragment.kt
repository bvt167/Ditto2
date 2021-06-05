package edu.uw.myapplication.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.NavGraphDirections
import edu.uw.myapplication.adapter.MoveAdapter
import edu.uw.myapplication.databinding.FragmentEvolutionsBinding
import edu.uw.myapplication.databinding.FragmentMovesBinding
import edu.uw.myapplication.model.EvolutionList
import edu.uw.myapplication.model.Move
import edu.uw.myapplication.model.MoveList
import edu.uw.myapplication.model.Pokemon
import edu.uw.myapplication.repository.DataRepository
import kotlinx.coroutines.launch

private const val MOVE_URL_PREFIX = "https://pokeapi.co/api/v2/move/"

class MovesFragment : Fragment() {

    private lateinit var binding: FragmentMovesBinding
    private val safeArgs: MovesFragmentArgs by navArgs()
    private lateinit var application: DittoApplication
    private lateinit var dataRepository: DataRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = context.applicationContext as DittoApplication
        dataRepository = application.dataRepository
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovesBinding.inflate(layoutInflater)
        val pokemon = safeArgs.pokemon

        with(binding) {
            var listOfMoves: List<Move> = listOf()
            lifecycleScope.launch {
                val moves = pokemon.moves
                moves.forEach {
                    if (it.version_group_details.last().level_learned_at != 0) { // Exclude TM/HM moves
                        listOfMoves += dataRepository.getMove(extractIdFromUrl(it.move.url, MOVE_URL_PREFIX))
                    }
                }
                // Sort by level learned
                listOfMoves = listOfMoves.sortedBy { move ->  pokemon.moves.first { it.move.name == move.name }.version_group_details.last().level_learned_at }

                rvMoves.adapter = MoveAdapter(listOfMoves, pokemon, application)
                tvLoading.visibility = View.GONE
                rvMoves.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    private fun extractIdFromUrl(url: String, idPrefix: String): Int {
        val size = url.length
        val stringID = url.substring(idPrefix.length, size - 1)
        return stringID.toInt()
    }

}