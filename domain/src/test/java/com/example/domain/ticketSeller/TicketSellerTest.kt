package com.example.domain.ticketSeller

import com.example.domain.model.Money
import com.example.domain.model.Movie
import com.example.domain.model.Ticket
import com.example.domain.model.seat.SeatPosition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.time.LocalDateTime

internal class TicketSellerTest {
    @Test
    fun `예상 결제 금액을 계산할 수 있다`() {
        val ticketSeller = TicketSeller()
        val movie = Movie(
            0, "", LocalDate.now(), LocalDate.now(), 120, ""
        )
        val dateTime = LocalDateTime.of(2023, 4, 10, 10, 0, 0)
        val seats = listOf<SeatPosition>(
            SeatPosition(3, 1),
            SeatPosition(5, 2)
        )

        val actual = ticketSeller.predictMoney(movie, dateTime, seats)
        val expected = Money(20300)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `티켓을 발급할 수 있다`() {
        val ticketSeller = TicketSeller()
        val movie = Movie(
            0, "", LocalDate.now(), LocalDate.now(), 120, ""
        )
        val dateTime = LocalDateTime.of(2023, 4, 10, 10, 0, 0)
        val seats = listOf<SeatPosition>(
            SeatPosition(3, 1),
            SeatPosition(5, 2)
        )

        val tickets = ticketSeller.issueTickets(movie, dateTime, seats)
        assertAll(
            "모든 티켓의 정보가 일치한다",
            {
                val actual = tickets.tickets[0]
                val expected = Ticket(movie, dateTime, SeatPosition(3, 1), Money(11500))
                assertThat(actual).isEqualTo(expected)
            },
            {
                val actual = tickets.tickets[1]
                val expected = Ticket(movie, dateTime, SeatPosition(5, 2), Money(8800))
                assertThat(actual).isEqualTo(expected)
            }
        )
    }
}
