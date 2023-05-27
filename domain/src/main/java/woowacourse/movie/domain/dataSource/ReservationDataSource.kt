package woowacourse.movie.domain.dataSource

import woowacourse.movie.domain.model.Reservation

interface ReservationDataSource {
    fun getData(): List<Reservation>
    fun addData(data: Reservation)
}
