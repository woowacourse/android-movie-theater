package woowacourse.movie.view.seatSelection

import woowacourse.movie.model.seat.Seat

object SeatSelectionFormatter {
    @JvmStatic
    fun seatsToUi(
        seats: List<Seat>,
        separator: String,
    ): String =
        seats
            .sortedBy { it.column.value }
            .sortedBy { it.row.value }
            .joinToString(separator) { seat -> seatToUi(seat) }

    @JvmStatic
    fun seatToUi(seat: Seat): String = "${rowToUi(seat.row.value)}${columnToUi(seat.column.value)}"

    @JvmStatic
    fun rowToUi(row: Int): String = (Char('A'.code) + row).toString()

    @JvmStatic
    fun columnToUi(column: Int): String = (column + 1).toString()
}
