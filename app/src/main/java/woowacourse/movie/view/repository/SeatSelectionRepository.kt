package woowacourse.movie.view.repository

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.ReservationDataSource
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.mapper.ReservationMapper.toDomain

class SeatSelectionRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()

    fun postReservation(reservation: ReservationViewData) {
        reservationDataSource.add(reservation.toDomain())
    }
}
