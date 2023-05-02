package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.ReservationDataSource

class SeatSelectionRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()

    fun postReservation(reservation: Reservation) {
        reservationDataSource.add(reservation)
    }
}
