package woowacourse.movie.fixture

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Ticket
import woowacourse.movie.model.seat.Col
import woowacourse.movie.model.seat.Row
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.Seats
import java.time.LocalDate
import java.time.LocalTime

const val SEOLLEUNG = "선릉"

val SEAT_A1_NOT_SELECTED = Seat(Row(0), Col(0), false)
val SEAT_A1 = Seat(Row(0), Col(0), isSelected = true)
val SEAT_A2 = Seat(Row(0), Col(1), isSelected = true)
val SEAT_C1 = Seat(Row(2), Col(0), isSelected = true)
val SEAT_E1 = Seat(Row(4), Col(0), isSelected = true)

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
        seats = Seats(seats),
    )
