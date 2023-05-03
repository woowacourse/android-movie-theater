package woowacourse.data.reservation

import woowacourse.data.movie.MovieDatabase
import java.time.LocalDateTime

object ReservationDatabase {
    private val _bookings = mutableListOf<ReservationEntity>(
        ReservationEntity(
            0,
            movie = MovieDatabase.selectMovie(1) ?: throw IllegalArgumentException(),
            LocalDateTime.of(2024, 3, 1, 10, 0),
            0,
            listOf<SeatEntity>(SeatEntity(2, 0, 0)),
        ),
    )
    val bookings: List<ReservationEntity> get() = _bookings.toList()

    fun selectBooking(id: Long): ReservationEntity? {
        return _bookings.find { it.id == id }
    }

    fun insertBooking(booking: ReservationEntity) {
        _bookings.add(booking)
    }

    fun getNewId(): Long = _bookings.last().id + 1
}
