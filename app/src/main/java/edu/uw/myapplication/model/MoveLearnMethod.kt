package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoveLearnMethod(
    val name: String,
    val url: String
): Parcelable
