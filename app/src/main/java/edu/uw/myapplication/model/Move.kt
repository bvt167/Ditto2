package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Move(
    val id: Int,
    val name: String,
    val accuracy: Int,
    val effect_chance: Int?,
    val pp: Int,
    val priority: Int,
    val power: Int,
    val damage_class: DamageClass,
    val type: Type,
    val flavor_text_entries: List<FlavorTextEntry>
): Parcelable
