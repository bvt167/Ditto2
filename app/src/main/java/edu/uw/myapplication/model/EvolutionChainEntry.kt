package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EvolutionChainEntry(
    val url: String
): Parcelable
