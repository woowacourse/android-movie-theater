package woowacourse.movie.domain.model

import woowacourse.movie.presentation.model.SeatModel
import java.io.Serializable

data class Seat(
    val column: String,
    val row: Int,
    val seatRank: SeatRank,
) : Serializable

fun Seat.toSeatModel(): SeatModel =
    SeatModel(
        column = column,
        row = row,
        seatRank = seatRank,
    )
