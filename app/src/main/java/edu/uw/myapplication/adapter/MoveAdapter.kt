package edu.uw.myapplication.adapter

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.R
import edu.uw.myapplication.databinding.ItemMoveBinding
import edu.uw.myapplication.model.FlavorTextEntry
import edu.uw.myapplication.model.Move
import edu.uw.myapplication.model.Pokemon
import java.util.*

class MoveAdapter(private val moves: List<Move>, private val pokemon: Pokemon, val application: DittoApplication): RecyclerView.Adapter<MoveAdapter.MoveListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveListViewHolder {
        val binding = ItemMoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoveListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoveListViewHolder, position: Int) {
        val currentMove = moves[position]
        val currentMoveEntry = pokemon.moves.first { it.move.name == currentMove.name }
        val levelLearned = currentMoveEntry.version_group_details.last().level_learned_at
        with(holder.binding) {
            if (currentMove.type.name == "unknown") {
                tvType.text = "???"
            } else {
                tvType.text = currentMove.type.name.uppercase(Locale.getDefault())
            }
            val background: GradientDrawable = application.getDrawable(R.drawable.item_type) as GradientDrawable
            background.setColor(application.getTypeColorByName(currentMove.type.name))
            tvType.background = background

            tvMove.text = currentMove.name.replaceFirstChar { it.uppercaseChar() }
            tvMoveCategory.text = currentMove.damage_class.name.uppercase()
            tvMoveFlavorText.text = getLatestEnglishFlavorText(currentMove.flavor_text_entries)
            tvLevelLearned.text = levelLearned.toString()
            tvPower.text = currentMove.power.toString()
            tvAccuracy.text = currentMove.accuracy.toString()
            tvPP.text = currentMove.pp.toString()
        }
    }

    override fun getItemCount(): Int = moves.size

    private fun getLatestEnglishFlavorText(entries: List<FlavorTextEntry> ): String {
        return entries.last { it.language.name == "en" }.flavor_text
    }

    class MoveListViewHolder(val binding: ItemMoveBinding): RecyclerView.ViewHolder(binding.root)
}