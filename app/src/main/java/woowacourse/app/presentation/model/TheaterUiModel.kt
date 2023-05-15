package woowacourse.app.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class TheaterUiModel(
    val id: Long,
    val rowSize: Int,
    val columnSize: Int,
    val sRankRange: @RawValue List<IntRange>,
    val aRankRange: @RawValue List<IntRange>,
    val bRankRange: @RawValue List<IntRange>,
) : Parcelable
