package woowacourse.movie.view.state.dataSource

import woowacourse.movie.view.data.ReservationViewData

class ReservationDataSource : DataSource<ReservationViewData> {
    override val value: List<ReservationViewData>
        get() = data

    override fun add(t: ReservationViewData) {
        data.add(t)
    }

    companion object {
        private val data: MutableList<ReservationViewData> = mutableListOf()
    }
}
