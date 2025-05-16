package woowacourse.movie.domain

import woowacourse.movie.domain.model.reservation.ReservationHistory
import woowacourse.movie.domain.model.ticketing.Ticket

interface ReservationRepository {
    fun getAll(onResult: (List<ReservationHistory>) -> Unit)

    fun insert(ticket: Ticket, onResult: (Result<Long>) -> Unit)
}
