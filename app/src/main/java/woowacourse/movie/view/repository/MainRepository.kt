package woowacourse.movie.view.repository

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.MovieDataSource
import woowacourse.movie.domain.dataSource.ReservationDataSource
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.mapper.MovieMapper.toView
import woowacourse.movie.view.mapper.ReservationMapper.toView

class MainRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()
    private val movieDataSource: DataSource<Movie> = MovieDataSource()

    fun requestMovie(): List<MovieViewData> {
        return movieDataSource.value.map { it.toView() }
    }

    fun requestReservation(): List<ReservationViewData> {
        return reservationDataSource.value.map { it.toView() }
    }
}
