package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatEntry(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
): Parcelable
