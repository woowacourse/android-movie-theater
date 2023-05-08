package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.data.dataSource.ReservationDataSource
import woowacourse.movie.data.database.MovieDao
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
