package woowacourse.movie.view.history.adapter.model

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.core.util.StringFormatter

sealed interface TickRvItem {
    data class TicketItem(
        val id: Long,
        val bookingDate: String,
        val bookingTime: String,
        val theaterName: String,
        val movieName: String,
    ) : TickRvItem
}

fun Ticket.toItem(): TickRvItem.TicketItem {
    return TickRvItem.TicketItem(
        id = id,
        movieName = title,
        bookingDate = StringFormatter.dotDateFormat(bookingDate),
        bookingTime = bookingTime.toString(),
        theaterName = theaterName,
    )
}
