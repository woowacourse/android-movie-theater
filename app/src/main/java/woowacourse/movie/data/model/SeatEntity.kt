package woowacourse.movie.data.model

import woowacourse.movie.domain.Seat

data class SeatEntity(
    val reservationId: Int,
    val row: Int,
    val column: Int
) {

    fun toDomain(): Seat = Seat(column, row)

    companion object {
        const val TABLE_NAME = "SEAT"
        const val RESERVATION_ID_COLUMN = "RESERVATION_ID"
        const val ROW_COLUMN = "SEAT_ROW"
        const val COLUMN_COLUMN = "SEAT_COLUMN"
    }
}
