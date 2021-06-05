package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EvolutionChain(
    val id: Int,
    val chain: Chain
): Parcelable
