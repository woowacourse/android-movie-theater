package woowacourse.movie.dataSource

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.domain.Reservation

class ReservationDataSource(private val movieDao: MovieDao) : DataSource<Reservation> {
    private val data: MutableList<Reservation> = movieDao.selectAllReservations().toMutableList()
    override val value: List<Reservation>
        get() = data

    override fun add(t: Reservation) {
        data.add(t)
        movieDao.insertReservation(
            movieDao.writableDatabase, t
        )
    }
}
