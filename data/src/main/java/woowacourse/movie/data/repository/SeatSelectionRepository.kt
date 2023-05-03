package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.data.dataSource.ReservationDataSource
import woowacourse.movie.domain.Reservation

class SeatSelectionRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()

    fun postReservation(reservation: Reservation) {
        reservationDataSource.add(reservation)
    }
}
