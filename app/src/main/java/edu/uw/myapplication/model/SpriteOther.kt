package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpriteOther(
    val `official-artwork`: OfficialArtwork?
): Parcelable
