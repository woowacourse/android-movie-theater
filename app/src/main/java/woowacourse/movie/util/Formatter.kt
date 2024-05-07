@file:JvmName("Formatter")

package woowacourse.movie.util

import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

fun MovieSelectedSeats.formatSeats(): String {
    return selectedSeats.joinToString(SELECTED_SEATS_SEPARATOR) { seat ->
        seat.row.formatSeatRow() + seat.column.toString()
    }
}

fun String.unFormatSeats(): MovieSelectedSeats {
    val seats = split(SELECTED_SEATS_SEPARATOR)
    val selectedSeats = MovieSelectedSeats(seats.size)
    seats.forEach {
        selectedSeats.selectSeat(MovieSeat(it.unFormatSeatRow(), it.unFormatSeatColumn()))
    }
    return selectedSeats
}

fun LocalDate.formatScreeningDate(): String {
    return format(DateTimeFormatter.ofPattern("yyyy.M.d"))
}

fun LocalTime.formatScreeningTime(): String {
    return format(DateTimeFormatter.ofPattern("HH:mm"))
}

private const val SELECTED_SEATS_SEPARATOR = ", "
