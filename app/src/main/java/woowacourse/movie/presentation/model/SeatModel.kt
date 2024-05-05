package woowacourse.movie.presentation.model

import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatRank

data class SeatModel(
    val column: String,
    val row: Int,
    val seatRank: SeatRank,
    var isSelected: Boolean = false,
)

fun SeatModel.toSeat(): Seat =
    Seat(
        column = column,
        row = row,
        seatRank = seatRank,
    )
