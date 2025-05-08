package woowacourse.movie.data.bookinghistory

import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDateTime

val bookingHistory1 = BookingHistory(
    1,
    "Movie",
    "Theater",
    LocalDateTime.of(2025, 5, 8, 22, 0),
    1,
    10_000,
    listOf(Seat.of(1, 1))
)

val bookingHistory2 = BookingHistory(
    2,
    "Movie2",
    "Theater2",
    LocalDateTime.of(2025, 5, 10, 19, 0),
    3,
    30_000,
    listOf(Seat.of(1, 1), Seat.of(2, 2), Seat.of(3, 3))
)

val bookingHistory3 = BookingHistory(
    1,
    "Movie3",
    "Theater3",
    LocalDateTime.of(2025, 5, 12, 14, 0),
    2,
    20_000,
    listOf(Seat.of(1, 1), Seat.of(3, 3))
)