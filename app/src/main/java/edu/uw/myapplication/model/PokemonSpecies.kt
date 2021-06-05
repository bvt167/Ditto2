package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonSpecies(
    val id: Int,
    val name: String,
    val gender_rate: Int?,
    val capture_rate: Int?,
    val base_happiness: Int?,
    val has_gender_differences: Boolean?,
    val flavor_text_entries: List<FlavorTextEntry>,
    val evolves_from_species: EvolvesFromSpecies?,
    val evolution_chain: EvolutionChainEntry
): Parcelable
