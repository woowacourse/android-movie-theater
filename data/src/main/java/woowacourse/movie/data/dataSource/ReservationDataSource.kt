package woowacourse.movie.data.dataSource

import woowacourse.movie.domain.Reservation

class ReservationDataSource : DataSource<Reservation> {
    override val value: List<Reservation>
        get() = data

    override fun add(t: Reservation) {
        data.add(t)
    }

    companion object {
        private val data: MutableList<Reservation> = mutableListOf()
    }
}
