package woowacourse.movie.domain.model

import woowacourse.movie.presentation.model.SeatModel

data class Seat(
    val column: String,
    val row: Int,
    val seatRank: SeatRank,
)

fun Seat.toSeatModel(): SeatModel =
    SeatModel(
        column = column,
        row = row,
        seatRank = seatRank,
    )
