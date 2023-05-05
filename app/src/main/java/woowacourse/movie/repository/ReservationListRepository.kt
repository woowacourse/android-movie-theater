package woowacourse.movie.repository

import domain.Reservation

interface ReservationListRepository {
    val reservations: List<Reservation>
}
