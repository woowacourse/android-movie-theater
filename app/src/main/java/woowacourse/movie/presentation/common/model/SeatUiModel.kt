package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.cinema.Seat

@Parcelize
data class SeatUiModel(
    val row: Int,
    val col: Int,
    val type: SeatTypeUiModel,
    val selected: Boolean = false,
) : Parcelable {
    fun toLabel(): String = "${'A' + row}${col + 1}"
}

fun Seat.toUiModel(): SeatUiModel = SeatUiModel(row, col, type.toUiModel())

fun SeatUiModel.toDomain(): Seat = Seat(row, col)
