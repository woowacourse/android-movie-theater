package woowacourse.movie.model.mapper

import com.example.domain.model.Ticket
import woowacourse.movie.model.TicketState

fun TicketState.asDomain(): Ticket = Ticket(
    theater.asDomain(),
    movie.asDomain(),
    dateTime,
    seatPositionState.asDomain(),
    discountedMoneyState.asDomain()
)

fun Ticket.asPresentation(): TicketState = TicketState(
    theater.asPresentation(),
    movie.asPresentation(),
    dateTime,
    position.asPresentation(),
    discountedMoney.asPresentation()
)
