package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.dataSource.ReservationDataSource

class ReservationRepository(private val dataSource: ReservationDataSource) {
    fun getData(): List<Reservation> {
        return dataSource.getData()
    }

    fun addData(data: Reservation) {
        dataSource.addData(data)
    }
}
