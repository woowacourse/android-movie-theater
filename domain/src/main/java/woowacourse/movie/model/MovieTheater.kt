package woowacourse.movie.model

class MovieTheater(
    val id: Long,
    private val rowRate: Map<SeatRate, List<Int>>,
    private val colLength: Int,
    val name: String,
) {
    fun seats(): List<Seat> =
        rowRate.flatMap { (seatRate, rateRows) ->
            rateRows.flatMap { row ->
                (0 until colLength).map { col ->
                    Seat(seatRate, row - 1, col)
                }
            }
        }

    companion object {
        val STUB_A =
            MovieTheater(
                0,
                mapOf(SeatRate.S to listOf(3, 4), SeatRate.A to listOf(5), SeatRate.B to listOf(1, 2)),
                4,
                "잠실 극장",
            )
        val STUB_B =
            MovieTheater(
                1,
                mapOf(SeatRate.S to listOf(3, 4), SeatRate.A to listOf(5), SeatRate.B to listOf(1, 2)),
                4,
                "선릉 극장",
            )
        val STUB_C =
            MovieTheater(
                2,
                mapOf(SeatRate.S to listOf(3, 4), SeatRate.A to listOf(5), SeatRate.B to listOf(1, 2)),
                4,
                "강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남 ",
            )
    }
}
