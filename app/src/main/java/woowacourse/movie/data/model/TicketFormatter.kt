package woowacourse.movie.data.model

import woowacourse.movie.data.model.mapper.TicketsMapper
import woowacourse.movie.data.model.uimodel.TicketsUIModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

object TicketFormatter {
    fun getPrice(ticketsUiModel: TicketsUIModel): String {
        val tickets = TicketsMapper.toDomain(ticketsUiModel)
        return NumberFormat.getNumberInstance(Locale.US).format(tickets.price.value)
    }

    fun getSeats(ticketsUiModel: TicketsUIModel): String {
        val seats = ticketsUiModel.list.map { ticket ->
            ticket.seat.row.toString() + ticket.seat.col.toString()
        }
        return seats.joinToString(",")
    }

    fun getDateTime(ticketsUiModel: TicketsUIModel): String {
        return ticketsUiModel.list[0].date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }
}
