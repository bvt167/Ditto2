package edu.uw.myapplication

import android.app.Application
import androidx.core.content.ContextCompat
import edu.uw.myapplication.repository.DataRepository

class DittoApplication: Application() {

    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()

        dataRepository = DataRepository()
    }

    fun getTypeColorByName(type: String): Int {
        val color =  when(type) {
            "normal" -> R.color.normal
            "fighting" -> R.color.fighting
            "flying" -> R.color.flying
            "poison" -> R.color.poison
            "ground" -> R.color.ground
            "rock" -> R.color.rock
            "bug" -> R.color.bug
            "ghost" -> R.color.ghost
            "steel" -> R.color.steel
            "fire" -> R.color.fire
            "water" -> R.color.water
            "grass" -> R.color.grass
            "electric" -> R.color.electric
            "psychic" -> R.color.psychic
            "ice" -> R.color.ice
            "dragon" -> R.color.dragon
            "dark" -> R.color.dark
            "fairy" -> R.color.fairy
            "unknown" -> R.color.unknown
            "shadow" -> R.color.shadow
            else -> R.color.white
        }
        return ContextCompat.getColor(this, color)
    }
}
