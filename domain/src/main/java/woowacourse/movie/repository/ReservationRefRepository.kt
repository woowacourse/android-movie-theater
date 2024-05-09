package woowacourse.movie.repository

import woowacourse.movie.model.ReservationRef
import woowacourse.movie.model.Seats

interface ReservationRefRepository {
    fun makeReservationRef(
        screeningId: Long,
        seats: Seats,
    ): Long

    fun reservationRefById(id: Long): ReservationRef?
}
