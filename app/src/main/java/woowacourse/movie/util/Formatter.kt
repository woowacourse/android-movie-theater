package woowacourse.movie.util

import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieTicket

fun Int.formatSeatRow(): Char {
    return (this + 'A'.code).toChar()
}

fun String.unFormatSeatRow(): Int {
    return this.first() - 'A'
}

fun Int.formatSeatColumn(): String {
    return (this + 1).toString()
}

fun String.unFormatSeatColumn(): Int {
    return substring(1).toInt()
}

fun MovieSeat.formatSeat(): String {
    return row.formatSeatRow() + column.formatSeatColumn()
}

fun MovieTicket.formatSeats(): String {
    return seats.selectedSeats.joinToString(", ") { seat ->
        seat.row.formatSeatRow() + seat.column.toString()
    }
}
