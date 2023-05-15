package com.example.domain.usecase

import com.example.domain.model.Movie
import com.example.domain.model.Theater
import com.example.domain.model.Tickets
import com.example.domain.model.seat.SeatPosition
import com.example.domain.ticketSeller.TicketSeller
import java.time.LocalDateTime

class GetIssuedTicketsUseCase(private val ticketSeller: TicketSeller = TicketSeller()) {
    operator fun invoke(
        theater: Theater,
        movie: Movie,
        dateTime: LocalDateTime,
        seats: List<SeatPosition>
    ): Tickets {
        return ticketSeller.issueTickets(theater, movie, dateTime, seats)
    }
}
