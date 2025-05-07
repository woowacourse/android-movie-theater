package woowacourse.movie.domain.model.cinema

class Seats(
    size: SeatingSize,
) {
    val seats: List<Seat> = buildSeats(size)

    val size: Int = seats.size

    private fun buildSeats(size: SeatingSize): List<Seat> =
        (STARTING_INDEX until size.rowSize).flatMap { row ->
            (STARTING_INDEX until size.colSize).map { col ->
                Seat(row, col)
            }
        }

    companion object {
        val DEFAULT_SEATS = Seats(SeatingSize(5, 4))
        private const val STARTING_INDEX = 0
    }
}
