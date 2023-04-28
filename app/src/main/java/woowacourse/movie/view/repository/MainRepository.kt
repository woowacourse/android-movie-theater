package woowacourse.movie.view.repository

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.ReservationDataSource

class MainRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()

    fun requestReservation(): List<Reservation> {
        return reservationDataSource.value
    }
}
