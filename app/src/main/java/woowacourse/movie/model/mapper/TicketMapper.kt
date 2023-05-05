package woowacourse.movie.model.mapper

import com.example.domain.model.Ticket
import woowacourse.movie.model.TicketState

fun TicketState.asDomain(): Ticket = Ticket(
    theaterName,
    movieState.asDomain(),
    dateTime,
    seatPositionState.asDomain(),
    discountedMoneyState.asDomain()
)

fun Ticket.asPresentation(): TicketState = TicketState(
    theaterName,
    movie.asPresentation(),
    dateTime,
    position.asPresentation(),
    discountedMoney.asPresentation()
)
