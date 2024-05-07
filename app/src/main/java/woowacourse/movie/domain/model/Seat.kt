package woowacourse.movie.domain.model

import java.io.Serializable

data class Seat(
    val column: String,
    val row: Int,
    val seatRank: SeatRank,
)

data class SeatModel(
    val column: String,
    val row: Int,
    val seatRank: SeatRank,
    var isSelected: Boolean = false,
) : Serializable
