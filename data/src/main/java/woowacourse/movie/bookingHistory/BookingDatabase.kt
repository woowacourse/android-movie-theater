package woowacourse.movie.bookingHistory

import java.time.LocalDateTime

object BookingDatabase {

    private const val ALREADY_RESERVED_SEAT = "이미 예약된 좌석입니다."

    private val _bookings = mutableListOf<BookingEntity>(
        BookingEntity(
            0,
            1,
            "해리 포터와 마법사의 돌",
            LocalDateTime.of(2024, 3, 1, 10, 0),
            0,
            listOf<SeatEntity>(SeatEntity(2, 0, 0)),
        ),
    )
    val bookings: List<BookingEntity> get() = _bookings.toList()

    fun selectBooking(id: Long): BookingEntity {
        return _bookings.find { it.id == id } ?: throw NoSuchElementException()
    }

    fun insertBooking(booking: BookingEntity) {
        if (_bookings.contains(booking)) {
            throw IllegalArgumentException(ALREADY_RESERVED_SEAT)
        }
        _bookings.add(booking)
    }

    fun getNewId(): Long = _bookings.last().id + 1
}
