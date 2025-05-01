package woowacourse.movie.fixture

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Seat
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket
import java.time.LocalDate
import java.time.LocalTime

val SEAT_A1_NOT_SELECTED = Seat("A1", false)
val SEAT_A1 = Seat("A1", true)
val SEAT_A2 = Seat("A2", true)
val SEAT_C1 = Seat("C1", true)
val SEAT_E1 = Seat("E1", true)

fun createTicket(
    name: String,
    seats: List<Seat>,
    headCount: Int = seats.size,
): Ticket =
    Ticket(
        theater = name,
        title = HARRY_POTTER,
        headCount = HeadCount(headCount),
        selectedDate = LocalDate.of(2028, 10, 13),
        selectedTime = LocalTime.of(11, 0),
        seats = Seats(seats),
    )
