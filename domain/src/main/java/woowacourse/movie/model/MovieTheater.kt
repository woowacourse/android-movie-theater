package woowacourse.movie.model

class MovieTheater(
    val id: Long,
    val seats: List<Seat>,
    val name: String,
) {
    fun selectedSeats() = seats.filter { it.state == SeatState.SELECTED }

    fun changeSeatState(
        row: Int,
        col: Int,
    ): MovieTheater {
        val updatedSeats =
            seats.map {
                if (it.row == row && it.col == col) {
                    it.changeState()
                } else {
                    it
                }
            }
        return MovieTheater(id, updatedSeats, name)
    }

    companion object {
        val STUB_A =
            MovieTheater(
                0,
                TheaterSeatsSystem.seats(
                    mapOf(
                        SeatRate.S to listOf(3, 4),
                        SeatRate.A to listOf(5),
                        SeatRate.B to listOf(1, 2),
                    ),
                    4,
                ),
                "잠실 극장",
            )
        val STUB_B =
            MovieTheater(
                1,
                TheaterSeatsSystem.seats(
                    mapOf(
                        SeatRate.S to listOf(3, 4),
                        SeatRate.A to listOf(5),
                        SeatRate.B to listOf(1, 2),
                    ),
                    4,
                ),
                "선릉 극장",
            )
        val STUB_C =
            MovieTheater(
                2,
                TheaterSeatsSystem.seats(
                    mapOf(
                        SeatRate.S to listOf(3, 4),
                        SeatRate.A to listOf(5),
                        SeatRate.B to listOf(1, 2),
                    ),
                    4,
                ),
                "강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남 ",
            )
    }
}
