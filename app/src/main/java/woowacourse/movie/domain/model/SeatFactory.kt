package woowacourse.movie.domain.model

class SeatFactory(
    val rows: Int,
    val columns: Int,
) {
    val seats: List<Seat> =
        buildList {
            for (row in 0 until rows) {
                for (column in 0 until columns) {
                    add(Seat(row, column))
                }
            }
        }

    companion object {
        fun default() = SeatFactory(5, 4)
    }
}
