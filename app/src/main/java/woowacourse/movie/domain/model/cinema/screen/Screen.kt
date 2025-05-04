package woowacourse.movie.domain.model.cinema.screen

class Screen(
    size: ScreenSize,
) {
    val seats: List<Seat> = buildSeats(size)

    private fun buildSeats(size: ScreenSize): List<Seat> =
        (STARTING_INDEX until size.rowSize).flatMap { row ->
            (STARTING_INDEX until size.colSize).map { col ->
                Seat(row, col)
            }
        }

    companion object {
        val DEFAULT_SCREEN = Screen(ScreenSize(5, 4))
        private const val STARTING_INDEX = 0
    }
}
