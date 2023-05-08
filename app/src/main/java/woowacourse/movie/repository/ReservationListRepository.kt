package woowacourse.movie.repository

import domain.Reservation

interface ReservationListRepository {
    var reservations: List<Reservation>
}
