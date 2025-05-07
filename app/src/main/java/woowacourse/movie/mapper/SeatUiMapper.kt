package woowacourse.movie.mapper

import woowacourse.movie.model.seat.Seat

fun Seat.toSeatLabel(): String {
    val rowName = 'A' + row.value
    val colNumber = col.value + 1
    return "$rowName$colNumber"
}
