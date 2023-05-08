package woowacourse.movie.repository

import woowacourse.movie.data.mock.MockReservationsFactory

object ReservationListRepositoryImpl : ReservationListRepository {
    override var reservations = MockReservationsFactory.makeReservations().toList()
}
