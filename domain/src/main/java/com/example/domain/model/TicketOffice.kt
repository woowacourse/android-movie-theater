package com.example.domain.model

import com.example.domain.discountPolicy.DefaultDiscountPolicy
import com.example.domain.discountPolicy.DiscountPolicy
import com.example.domain.model.seat.SeatPosition
import java.time.LocalDateTime

class TicketOffice(private val discountPolicy: DiscountPolicy = DefaultDiscountPolicy()) {

    fun predictMoney(movie: Movie, dateTime: LocalDateTime, seats: List<SeatPosition>): Money {
        return Money(seats.sumOf { discountPolicy.discount(movie, dateTime, it).value })
    }

    fun issueTickets(
        theater: Theater,
        movie: Movie,
        dateTime: LocalDateTime,
        seats: List<SeatPosition>
    ): Tickets {
        return Tickets(seats.map { issueTicket(theater, movie, dateTime, it) })
    }

    private fun issueTicket(
        theater: Theater,
        movie: Movie,
        dateTime: LocalDateTime,
        seatPosition: SeatPosition
    ): Ticket {
        val discountedMoney = discountPolicy.discount(movie, dateTime, seatPosition)
        return Ticket(theater, movie, dateTime, seatPosition, discountedMoney)
    }
}
