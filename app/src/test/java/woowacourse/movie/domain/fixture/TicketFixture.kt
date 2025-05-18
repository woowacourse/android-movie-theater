package woowacourse.movie.domain.fixture

import woowacourse.movie.domain.model.PeopleCount
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Column
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDate
import java.time.LocalTime

val ticketFixture1 =
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

val ticketFixture2 =
    Ticket(
        id = 2L,
        title = "Inception",
        bookingDate = LocalDate.of(2025, 5, 11),
        bookingTime = LocalTime.of(16, 0),
        theaterName = "롯데시네마 월드타워",
        count = PeopleCount(2),
        price = 28000,
        seats = setOf(Seat(Column(2), Row(3)), Seat(Column(2), Row(4))),
    )

val ticketFixture3 =
    Ticket(
        id = 3L,
        title = "Dune: Part Two",
        bookingDate = LocalDate.of(2025, 5, 12),
        bookingTime = LocalTime.of(21, 15),
        theaterName = "메가박스 코엑스",
        count = PeopleCount(1),
        price = 15000,
        seats = setOf(Seat(Column(5), Row(6))),
    )

val ticketFixtures = listOf(ticketFixture1, ticketFixture2, ticketFixture3)
