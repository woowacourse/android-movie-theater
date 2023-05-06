package woowacourse.movie.repository

import woowacourse.movie.data.mock.MockReservationsFactory

object ReservationListRepositoryImpl : ReservationListRepository {
    override val reservations = MockReservationsFactory.makeReservations().toMutableList()
}
