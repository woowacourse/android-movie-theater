package woowacourse.movie.model.mapper

import com.example.domain.model.Ticket
import com.example.domain.model.Tickets
import com.example.domain.model.seat.SeatPosition
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState

fun TicketsState.asDomain(): Tickets = Tickets(
    positions.map {
        Ticket(
            movieState.asDomain(),
            dateTime,
            it.asDomain()
        )
    }
)

fun Tickets.asPresentation(): TicketsState = TicketsState(
    "영화관",
    this.tickets.first().movie.asPresentation(),
    this.tickets.first().dateTime,
    this.tickets.map { it.position.asPresentation() }
)

fun SeatPosition.asPresentation(): SeatPositionState = SeatPositionState(
    row.ordinal,
    column.ordinal
)
