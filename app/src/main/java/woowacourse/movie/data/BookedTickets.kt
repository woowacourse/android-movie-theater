package woowacourse.movie.data

import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime

object BookedTickets {

    // fake data
    val tickets = mutableListOf<TicketModel>(
        TicketModel(
            1,
            LocalDateTime.of(2024, 3, 1, 9, 0),
            3,
            33000,
            listOf("A1", "B1", "C1")
        ),
        TicketModel(
            2,
            LocalDateTime.of(2024, 3, 10, 22, 0),
            3,
            33000,
            listOf("A1", "D1")
        ),
        TicketModel(
            3,
            LocalDateTime.of(2024, 1, 1, 15, 0),
            3,
            11000,
            listOf("A1", "B1")
        ),
        TicketModel(
            4,
            LocalDateTime.of(2024, 1, 1, 9, 0),
            3,
            33000,
            listOf("A1", "B1", "C1")
        )
    )
}
