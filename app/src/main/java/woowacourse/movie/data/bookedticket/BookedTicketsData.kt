package woowacourse.movie.data.bookedticket

import woowacourse.movie.presentation.model.TicketModel

interface BookedTicketsData {
    fun getTickets(): List<TicketModel>
    fun addTickets(ticketModel: TicketModel)
}
