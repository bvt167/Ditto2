package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sprites(
    val back_female: String?,
    val back_shiny_female: String?,
    val back_default: String?,
    val front_female: String?,
    val front_shiny_female: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?,
    val other: SpriteOther
): Parcelable
