package woowacourse.movie.view.seatSelection

import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.model.seat.SeatGridElement

object SeatSelectionFormatter {
    @JvmStatic
    fun rowToUi(row: SeatGridElement): String = (Char('A'.code) + row.value).toString()

    @JvmStatic
    fun columnToUi(column: SeatGridElement): String = (column.value + 1).toString()

    @JvmStatic
    fun seatsToUi(
        seats: List<SeatEntity>,
        separator: String,
    ): String =
        seats
            .sortedWith(compareBy({ it.row }, { it.column }))
            .joinToString(separator) { seat -> seatToUi(seat) }

    @JvmStatic
    fun seatToUi(seat: SeatEntity): String = "${rowToUi(seat.row)}${columnToUi(seat.column)}"

    @JvmStatic
    fun rowToUi(row: Int): String = (Char('A'.code) + row).toString()

    @JvmStatic
    fun columnToUi(column: Int): String = (column + 1).toString()
}
