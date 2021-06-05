package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EvolutionDetail(
    val item: Item?,
    val min_level: Int?
): Parcelable
