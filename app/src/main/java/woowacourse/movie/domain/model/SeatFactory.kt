package woowacourse.movie.domain.model

class SeatFactory(
    val rows: Int,
    val columns: Int,
) {
    fun createSeats(): List<Seat> {
        val seats = mutableListOf<Seat>()
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                seats.add(Seat(row, column))
            }
        }
        return seats
    }

    companion object {
        fun default() = SeatFactory(5, 4)
    }
}
