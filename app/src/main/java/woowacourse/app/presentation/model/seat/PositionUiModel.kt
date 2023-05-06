package woowacourse.app.presentation.model.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PositionUiModel(
    val row: Int,
    val column: Int,
) : Parcelable
