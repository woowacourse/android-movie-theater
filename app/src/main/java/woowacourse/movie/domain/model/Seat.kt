package woowacourse.movie.domain.model

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
)
