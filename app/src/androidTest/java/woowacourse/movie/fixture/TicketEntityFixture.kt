package woowacourse.movie.fixture

import woowacourse.movie.data.db.TicketEntity
import woowacourse.movie.domain.model.seat.Column
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDateTime

val ticketEntity1 =
    TicketEntity(
        bookingDateTime = LocalDateTime.of(2025, 6, 30, 12, 0, 0),
        theaterName = "강남 극장",
        movieTitle = "개구리 중사 케로로",
        ticketCount = 3,
        ticketPrice = 39000,
        seats =
            setOf(
                Seat(
                    Column(1),
                    Row(1),
                ),
                Seat(
                    Column(1),
                    Row(1),
                ),
            ),
    )

val ticketEntity2 =
    TicketEntity(
        bookingDateTime = LocalDateTime.of(2025, 6, 30, 12, 0, 0),
        theaterName = "선릉 극장",
        movieTitle = "따끈 따끈 베이커리",
        ticketCount = 2,
        ticketPrice = 26000,
        seats = setOf(Seat(Column(1), Row(2))),
    )
