package woowacourse.movie.domain.model.cinema

data class Seat(
    val row: Int,
    val col: Int,
) {
    val type: SeatType = SeatType.fromRow(row)
}
