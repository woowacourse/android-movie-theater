package woowacourse.movie.datasource

import woowacourse.movie.domain.dataSource.ReservationDataSource
import woowacourse.movie.domain.model.Reservation

class ReservationDataSource :
    ReservationDataSource {
    override fun getData(): List<Reservation> {
        return data
    }

    override fun addData(data: Reservation) {
        Companion.data.add(data)
    }

    companion object {
        private val data: MutableList<Reservation> = mutableListOf()
    }
}
