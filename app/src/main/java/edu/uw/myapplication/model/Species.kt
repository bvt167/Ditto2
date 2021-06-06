package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Species(
    val flavor_text_entries: List<FlavorText>
): Parcelable