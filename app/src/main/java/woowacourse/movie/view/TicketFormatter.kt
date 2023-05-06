package woowacourse.movie.view

import woowacourse.movie.view.mapper.TicketsMapper
import woowacourse.movie.view.model.TicketsUiModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

object TicketFormatter {
    fun getPrice(ticketsUiModel: TicketsUiModel): String {
        val tickets = TicketsMapper.toDomain(ticketsUiModel)
        return NumberFormat.getNumberInstance(Locale.US).format(tickets.price.value)
    }

    fun getSeats(ticketsUiModel: TicketsUiModel): String {
        val seats = ticketsUiModel.list.map { ticket ->
            ticket.seat.row.toString() + ticket.seat.col.toString()
        }
        return seats.joinToString(",")
    }

    fun getDateTime(ticketsUiModel: TicketsUiModel): String {
        return ticketsUiModel.list[0].date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }
}
