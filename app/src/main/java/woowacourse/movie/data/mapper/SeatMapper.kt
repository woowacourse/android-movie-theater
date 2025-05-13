package woowacourse.movie.data.mapper

import woowacourse.movie.domain.model.cinema.Seat

private const val SEAT_DELIMITER = ""
private const val COORDINATE_DELIMITER = ","

fun List<Seat>.convertToString(): String =
    this.joinToString(SEAT_DELIMITER) { "${it.row}$COORDINATE_DELIMITER${it.col}" }

fun String.convertToSeats(): List<Seat> {
    val seats = this.split(SEAT_DELIMITER)
    return seats.mapNotNull { seat ->
        val (row, col) = seat.split(COORDINATE_DELIMITER).map { it.toIntOrNull() }
        if(row != null && col != null) Seat(row, col) else null
    }
}
