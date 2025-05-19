package woowacourse.movie.data.reservation

import woowacourse.movie.domain.ticket.Reservation

interface ReservationData {
    fun add(reservation: Reservation)

    fun reservations(): List<Reservation>
}
