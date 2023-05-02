package woowacourse.movie.view.repository

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.MovieDataSource
import woowacourse.movie.domain.dataSource.ReservationDataSource

class MainRepository {
    private val reservationDataSource: DataSource<Reservation> = ReservationDataSource()
    private val movieDataSource: DataSource<Movie> = MovieDataSource()

    fun requestMovie(): List<Movie> {
        return movieDataSource.value
    }

    fun requestReservation(): List<Reservation> {
        return reservationDataSource.value
    }
}
