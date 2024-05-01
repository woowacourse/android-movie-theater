package woowacourse.movie.model

class MovieTheater(
    private val rowRate: Map<SeatRate, List<Int>>,
    private val colLength: Int,
    private val name: String,
) {
    fun seats(): List<Seat> =
        rowRate.flatMap { (seatRate, rateRows) ->
            rateRows.flatMap { row ->
                (0 until colLength).map { col ->
                    Seat(seatRate, row - 1, col)
                }
            }
        }
}
