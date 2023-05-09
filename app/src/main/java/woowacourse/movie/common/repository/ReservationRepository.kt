package woowacourse.movie.common.repository

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.dataSource.DataSource
import woowacourse.movie.dataSource.ReservationDataSource
import woowacourse.movie.domain.Reservation

class ReservationRepository(movieDao: MovieDao) {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource(movieDao)

    fun requestReservation(): List<Reservation> {
        return reservationDataSource.value
    }

    fun postReservation(reservation: Reservation) {
        reservationDataSource.add(reservation)
    }
}
