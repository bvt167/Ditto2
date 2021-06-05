package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chain(
    val is_baby: Boolean,
    val species: PokemonSpeciesEntry,
    val evolution_details: List<EvolutionDetail>,
    val evolves_to: List<Chain>
): Parcelable
