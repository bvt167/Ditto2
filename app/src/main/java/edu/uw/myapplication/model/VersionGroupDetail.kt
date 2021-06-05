package edu.uw.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VersionGroupDetail(
    val level_learned_at: Int,
    val version_group: VersionGroup,
    val move_learn_method: MoveLearnMethod
): Parcelable
