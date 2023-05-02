package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.ReservationDataSource

class ReservationListRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()

    fun requestReservation(): List<Reservation> {
        return reservationDataSource.value
    }
}
