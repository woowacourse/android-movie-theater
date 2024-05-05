package woowacourse.movie.model

class MovieTheater(
    val id: Long,
    val seats: List<Seat>,
    val name: String,
) {
    fun alreadySelected(): List<Seat> = seats.filter { it.state == SeatState.RESERVED }

    fun reserveSeat(selectedSeats: SelectedSeats): MovieTheater {
        val selectedSeatsMap = selectedSeats.selectedSeats.associateBy { it.row to it.col }

        val updatedSeats =
            seats.map { seat ->
                selectedSeatsMap[seat.row to seat.col]?.let { seat.copy(state = it.state) } ?: seat
            }

        return MovieTheater(id, updatedSeats, name)
    }

    fun seat(
        row: Int,
        col: Int,
    ) = seats.firstOrNull { it.row == row && it.col == col } ?: error("해당 극장에는 $row $col 에 해당하는 좌석이 없습니다.")

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
