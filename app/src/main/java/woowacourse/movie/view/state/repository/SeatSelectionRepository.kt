package woowacourse.movie.view.state.repository

import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.state.dataSource.DataSource
import woowacourse.movie.view.state.dataSource.ReservationDataSource

class SeatSelectionRepository {
    private val reservationDataSource: DataSource<ReservationViewData> = ReservationDataSource()

    fun postReservation(reservation: ReservationViewData) {
        reservationDataSource.add(reservation)
    }
}
