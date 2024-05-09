package woowacourse.movie.data

import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.model.Reservation

class DefaultMovieRepository(
    private val movieDao: MovieDao
) : MovieRepository {
    override fun loadReservedMovies(): Result<List<Reservation>> = runCatching {
        movieDao.loadAllReservations().toReservations()
    }

    override fun loadReservedMovie(id: Long): Result<Reservation> = runCatching {
        movieDao.loadReservationWithSeats(id).toReservation()
    }

    override fun saveReservation(reservation: Reservation): Result<Unit> = runCatching {
        val reservationId = movieDao.saveReservation(reservation.toReservationEntity())
        val seatEntities = reservation.toSeatEntities(reservationId)
        movieDao.saveMovieSeats(seatEntities)
    }

    override fun deleteAllReservedMovie(): Result<Unit> = runCatching {
        movieDao.deleteAllReservations()
    }
}