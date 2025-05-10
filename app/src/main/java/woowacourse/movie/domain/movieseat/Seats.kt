package woowacourse.movie.domain.movieseat

import java.io.Serializable

class Seats(private val seats: Set<Seat> = emptySet()) : Serializable {
    val selectedSeats get() = seats.toList()

    fun addSeat(seat: Seat): Seats = Seats(seats + seat)

    fun removeSeat(seat: Seat): Seats = Seats(seats - seat)

    fun selectedLimit(limit: Int): Boolean = seats.size >= limit

    fun canSelect(limit: Int): Boolean = seats.size == limit

    fun reservationPrice() = seats.sumOf { it.seatPrice() }
}

fun Seats.toSeatString(): String {
    return selectedSeats
        .map { it.toDisplayName() }
        .sorted()
        .joinToString(", ")
}

fun Seat.toDisplayName(): String {
    val rowChar = 'A' + position.row
    return "$rowChar${position.column + 1}"
}

fun String.toSeat(): Seat {
    val rowChar = this[0]
    val columnNumber = this.substring(1).toInt()

    val row = rowChar - 'A'
    val column = columnNumber - 1

    return Seat(Position(row, column))
}

fun String.toSeats(): Seats {
    return Seats(
        this.split(",")
            .map { it.trim().toSeat() }
            .toSet(),
    )
}
