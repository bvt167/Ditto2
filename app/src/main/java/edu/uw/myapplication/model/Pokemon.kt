package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val forms: List<Form>,
    val abilities: List<AbilityEntry>,
    val moves: List<MovesEntry>,
    val sprites: Sprites,
    val stats: List<StatEntry>,
    val types: List<TypeEntry>
): Parcelable
