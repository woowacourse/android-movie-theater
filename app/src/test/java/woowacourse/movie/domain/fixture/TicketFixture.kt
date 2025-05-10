package woowacourse.movie.domain.fixture

import woowacourse.movie.domain.model.PeopleCount
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Column
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDate
import java.time.LocalTime

val ticketFixture =
    Ticket(
        id = 1L,
        title = "Interstellar",
        bookingDate = LocalDate.of(2025, 5, 10),
        bookingTime = LocalTime.of(19, 30),
        theaterName = "CGV 강남",
        count = PeopleCount(1),
        price = 24000,
        seats = setOf(Seat(Column(1), Row(1))),
    )
