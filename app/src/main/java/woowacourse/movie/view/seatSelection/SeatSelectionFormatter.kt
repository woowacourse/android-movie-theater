package woowacourse.movie.view.seatSelection

import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.SeatGridElement

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
    fun seatToUi(seat: Seat): String = "${rowToUi(seat.row)}${columnToUi(seat.column)}"

    @JvmStatic
    fun rowToUi(row: SeatGridElement): String = (Char('A'.code) + row.value).toString()

    @JvmStatic
    fun columnToUi(column: SeatGridElement): String = (column.value + 1).toString()
}
