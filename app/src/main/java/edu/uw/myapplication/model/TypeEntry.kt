package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeEntry(
    val slot: Int,
    val type: Type
): Parcelable
