package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.data.dataSource.ReservationDataSource
import woowacourse.movie.domain.Reservation

class ReservationListRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()

    fun requestReservation(): List<Reservation> {
        return reservationDataSource.value
    }
}
