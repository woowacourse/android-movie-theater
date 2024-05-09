package woowacourse.movie.data

import woowacourse.movie.model.Reservation

interface MovieRepository {
    fun loadReservedMovies(): Result<List<Reservation>>

    fun loadReservedMovie(id: Long): Result<Reservation>

    fun saveReservation(reservation: Reservation): Result<Long>

    fun deleteAllReservedMovie(): Result<Unit>
}