package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlavorText(
    val flavor_text: String,
    val language: LanguageObj
): Parcelable