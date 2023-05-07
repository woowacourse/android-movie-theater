package woowacourse.movie.presentation

import woowacourse.movie.data.bookedticket.BookedTicketsData
import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime

object MockBookedTicketsData : BookedTicketsData {

    // fake data
    private val tickets = mutableListOf<TicketModel>(
        TicketModel(
            1,
            "선릉",
            LocalDateTime.of(2024, 3, 1, 9, 0),
            3,
            33000,
            listOf("A1", "B1", "C1"),
        ),
        TicketModel(
            2,
            "잠실",
            LocalDateTime.of(2024, 3, 10, 22, 0),
            3,
            33000,
            listOf("A1", "D1"),
        ),
        TicketModel(
            3,
            "아주 아주 아주 긴 이름의 극장",
            LocalDateTime.of(2024, 1, 1, 15, 0),
            3,
            11000,
            listOf("A1", "B1"),
        ),
        TicketModel(
            4,
            "강남",
            LocalDateTime.of(2024, 1, 1, 9, 0),
            3,
            33000,
            listOf("A1", "B1", "C1"),
        ),
    )

    override fun getTickets(): List<TicketModel> = tickets.toList()

    override fun addTickets(ticketModel: TicketModel) {
        tickets.add(ticketModel)
    }
}
