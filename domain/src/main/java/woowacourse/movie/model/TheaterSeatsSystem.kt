package woowacourse.movie.model

object TheaterSeatsSystem {
    fun seats(
        rowRate: Map<SeatRate, List<Int>>,
        colLength: Int,
    ): List<Seat> =
        rowRate.flatMap { (seatRate, rateRows) ->
            rateRows.flatMap { row ->
                (0 until colLength).map { col ->
                    Seat(seatRate, row - 1, col)
                }
            }
        }
}
