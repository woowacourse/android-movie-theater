package woowacourse.movie.fixture.repository

import woowacourse.movie.data.MovieData
import woowacourse.movie.data.TheaterData
import woowacourse.movie.domain.model.HeadCount
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.Seats
import java.time.LocalDateTime

object TicketData {
    val tickets: List<Ticket> =
        listOf(
            Ticket(
                MovieData.HARRY_POTTER_01,
                TheaterData.SEOLLEUNG,
                LocalDateTime.of(2025, 5, 5, 15, 0),
                HeadCount(2),
                Seats(setOf(Seat.from(2, 2), Seat.from(2, 3))),
            ),
            Ticket(
                MovieData.HARRY_POTTER_01,
                TheaterData.GANGNAM,
                LocalDateTime.of(2025, 5, 5, 17, 0),
                HeadCount(2),
                Seats(setOf(Seat.from(2, 2), Seat.from(2, 3))),
            ),
            Ticket(
                MovieData.HARRY_POTTER_02,
                TheaterData.SEOLLEUNG,
                LocalDateTime.of(2025, 5, 10, 12, 0),
                HeadCount(2),
                Seats(setOf(Seat.from(2, 2), Seat.from(2, 3))),
            ),
        )
}
