package woowacourse.movie.model

class MovieTheater(
    val id: Long,
    val name: String,
    val seats: List<Seat> = emptyList(),
) {
    fun alreadySelected(): List<Seat> = seats.filter { it.state == SeatState.RESERVED }

    fun reserveSeat(selectedSeats: SelectedSeats): MovieTheater {
        val selectedSeatsMap = selectedSeats.selectedSeats.associateBy { it.row to it.col }

        val updatedSeats =
            seats.map { seat ->
                selectedSeatsMap[seat.row to seat.col]?.let { seat.copy(state = it.state) } ?: seat
            }

        return MovieTheater(id, name, updatedSeats)
    }

    fun seat(
        row: Int,
        col: Int,
    ) = seats.firstOrNull { it.row == row && it.col == col }
        ?: error("해당 극장에는 $row $col 에 해당하는 좌석이 없습니다.")

    companion object {
        val seatSystem =
            TheaterSeatsSystem(
                mapOf(
                    SeatRate.S to listOf(3, 4),
                    SeatRate.A to listOf(5),
                    SeatRate.B to listOf(1, 2),
                ),
                4,
            )

        val STUB_A =
            MovieTheater(
                0,
                "잠실 극장",
                seatSystem.seats(),
            )

        val STUB_B =
            MovieTheater(
                1,
                "선릉 극장",
                seatSystem.seats(),
            )
        val STUB_C =
            MovieTheater(
                2,
                "강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남 ",
                seatSystem.seats(),
            )
    }
}
