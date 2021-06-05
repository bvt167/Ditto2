package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovesEntry(
    val move: MoveEntry,
    val version_group_details: List<VersionGroupDetail>
): Parcelable
