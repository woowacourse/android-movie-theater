package woowacourse.movie.domain.model.reservation

import woowacourse.movie.domain.model.ticketing.Ticket

data class ReservationHistory(
    val id: Long,
    val ticket: Ticket
)
