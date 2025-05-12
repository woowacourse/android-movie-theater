package woowacourse.movie.domain

import woowacourse.movie.domain.model.reservation.ReservationHistory
import woowacourse.movie.domain.model.ticketing.Ticket

interface ReservationRepository {
    fun getAll(): List<ReservationHistory>

    fun insert(ticket: Ticket): Result<Unit>
}
