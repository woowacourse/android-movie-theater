package woowacourse.data.bookingHistory

import woowacourse.data.movie.MovieDatabase
import java.time.LocalDateTime

object BookingDatabase {
    private val _bookings = mutableListOf<BookingEntity>(
        BookingEntity(
            0,
            movie = MovieDatabase.selectMovie(1),
            LocalDateTime.of(2024, 3, 1, 10, 0),
            0,
            listOf<SeatEntity>(SeatEntity(2, 0, 0)),
        ),
    )
    val bookings: List<BookingEntity> get() = _bookings.toList()

    fun selectBooking(id: Long): BookingEntity? {
        return _bookings.find { it.id == id }
    }

    fun insertBooking(booking: BookingEntity) {
        _bookings.add(booking)
    }

    fun getNewId(): Long = _bookings.last().id + 1
}
