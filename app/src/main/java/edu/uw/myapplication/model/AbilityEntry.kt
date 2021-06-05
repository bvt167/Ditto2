package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AbilityEntry(
    val is_hidden: Boolean,
    val slot: Int,
    val ability: Ability
): Parcelable
