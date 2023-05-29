package com.example.domain.model.seat

import com.example.domain.model.Money
import com.example.domain.model.Movie
import com.example.domain.model.Theater
import com.example.domain.model.Ticket
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class TicketTest {

    private val movie = Movie(0, "", LocalDate.now(), LocalDate.now(), 120, "")
    private val theater = Theater(theaterId = 0, theaterName = "")
    private val ticket = Ticket(
        theater = theater, movie = movie,
        dateTime = LocalDateTime.of(2023, 4, 20, 12, 0, 0),
        position = SeatPosition(2, 3),
        discountedMoney = Money(10000)
    )

    @Test
    fun `티켓 좌석 정보로 랭크와 해당 티켓의 원본 가격을 알 수 있다`() {
        val actual = ticket.originMoney
        val expected = Money(10000)
        assertThat(actual).isEqualTo(expected)
    }
}
