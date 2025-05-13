package woowacourse.movie.domain.reservation

import woowacourse.movie.domain.ticket.Reservation

interface ReservationSortingPolicy {
    fun sort(reservations: List<Reservation>): List<Reservation>
}

class ShowtimeAscendingPolicy : ReservationSortingPolicy {
    override fun sort(reservations: List<Reservation>): List<Reservation> = reservations.sortedBy(Reservation::showtime)
}
