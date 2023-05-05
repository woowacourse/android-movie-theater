package woowacourse.movie.repository

import woowacourse.movie.MockReservationsFactory

object ReservationListRepositoryImpl : ReservationListRepository {
    override val reservations = MockReservationsFactory.makeReservations().toMutableList()
}
