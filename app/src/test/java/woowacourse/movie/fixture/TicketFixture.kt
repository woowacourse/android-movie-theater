package woowacourse.movie.fixture

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Seat
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket
import java.time.LocalDate
import java.time.LocalTime

const val SEOLLEUNG = "선릉"

val SEAT_A1 = Seat(0, 0)
val SEAT_A2 = Seat(0, 1)
val SEAT_C1 = Seat(2, 0)
val SEAT_E1 = Seat(4, 0)

fun createTicket(
    name: String,
    seats: List<Seat>,
    headCount: Int = seats.size,
): Ticket =
    Ticket(
        theater = name,
        title = "해리 포터와 마법사의 돌",
        headCount = HeadCount(headCount),
        selectedDate = LocalDate.of(2028, 10, 13),
        selectedTime = LocalTime.of(11, 0),
        seats = Seats(seats.toMutableSet()),
    )
