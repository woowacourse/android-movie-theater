package woowacourse.movie.util

import woowacourse.movie.ticket.Position
import woowacourse.movie.ticket.Seat

fun String.toSeat(): Seat {
    val row = this[0] - 'A'
    val col = this.substring(1).toInt()

    return Seat.valueOf(Position(row, col))
}
