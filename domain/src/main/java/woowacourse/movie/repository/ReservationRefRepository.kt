package woowacourse.movie.repository

import woowacourse.movie.model.ReservationRef

interface ReservationRefRepository {
    fun makeReservationRef(
        screeningId: Long,
        seats: String,
    ): Long

    fun reservationRefById(id: Long): ReservationRef?
}
