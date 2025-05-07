package woowacourse.movie.fixture

import woowacourse.movie.domain.model.HeadCount
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seats
import java.time.LocalDateTime

val INITIAL_TICKET =
    Ticket(
        movie = HARRY_POTTER,
        theater = SEOLLEUNG,
    )

val BOOKED_TICKET =
    Ticket(
        movie = HARRY_POTTER,
        theater = SEOLLEUNG,
        showtime = LocalDateTime.of(2025, 12, 31, 12, 0),
        headCount = HeadCount(2),
        seats = Seats(setOf(B3, D2)),
    )
