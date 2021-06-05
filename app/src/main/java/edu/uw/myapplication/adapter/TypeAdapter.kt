package edu.uw.myapplication.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uw.myapplication.DittoApplication
import edu.uw.myapplication.R
import edu.uw.myapplication.databinding.ItemTypeBinding
import edu.uw.myapplication.model.TypeEntry
import java.util.*

class TypeAdapter(private val types: List<TypeEntry>, private val application: DittoApplication): RecyclerView.Adapter<TypeAdapter.TypeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeListViewHolder {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context))
        return TypeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TypeListViewHolder, position: Int) {
        val typeEntry: TypeEntry = types[position]
        with(holder.binding) {
            if (typeEntry.type.name == "unknown") {
                tvType.text = "???"
            } else {
                tvType.text = typeEntry.type.name.uppercase(Locale.getDefault())
            }
            val background: GradientDrawable = application.getDrawable(R.drawable.item_type) as GradientDrawable
            background.setColor(application.getTypeColorByName(typeEntry.type.name))
            tvType.background = background
        }
    }

    override fun getItemCount(): Int = types.size

    class TypeListViewHolder(val binding: ItemTypeBinding): RecyclerView.ViewHolder(binding.root)
}
