package woowacourse.movie.view.state.repository

import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.state.dataSource.DataSource
import woowacourse.movie.view.state.dataSource.MovieDataSource
import woowacourse.movie.view.state.dataSource.ReservationDataSource

class MainRepository {
    private val reservationDataSource: DataSource<ReservationViewData> = ReservationDataSource()
    private val movieDataSource: DataSource<MovieViewData> = MovieDataSource()

    fun requestMovie(): List<MovieViewData> {
        return movieDataSource.value
    }

    fun requestReservation(): List<ReservationViewData> {
        return reservationDataSource.value
    }
}
